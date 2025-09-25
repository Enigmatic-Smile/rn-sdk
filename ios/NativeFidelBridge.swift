//
//  NativeFidelBridge.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 06/01/22.
//

import Foundation
import React
import Fidel
import analytics

@objc(NativeFidelBridge)
class NativeFidelBridge: RCTEventEmitter {

    private var eventObservers = [BridgeLibraryEvent: EventObserver]()
    private let imageAdapter = FLRNImageFromRNAdapter()
    private let setupAdapter: FidelSetupAdapter
    private let constantsProvider = ExportedConstantsProvider()

    override init() {
        setupAdapter = FidelSetupAdapter(imageAdapter: imageAdapter)
        super.init()
    }

    @objc(setup:)
    func setup(with jsSetupInfo: NSDictionary) {
        setupAdapter.setup(with: jsSetupInfo)
    }

    @objc(start)
    func start() {
        guard let startViewController = UIApplication.shared.delegate?.window??.rootViewController else {
            return
        }
        Fidel.start(from: startViewController)
    }

    @objc(identifyMetricsDataSource:version:)
    func identifyMetricsDataSource(name: String, version: String) {
        let reactNativeSdkDetails = SdkDetails(name: name, version: version)
        Analytics().identifyMultiplatformDataSource(sdkDetails: reactNativeSdkDetails)
    }

    override func supportedEvents() -> [String]! {
        return BridgeLibraryEvent.allCases.map { $0.rawValue }
    }

    override func addListener(_ eventName: String!) {
        super.addListener(eventName)
        guard let event = BridgeLibraryEvent(rawValue: eventName) else {
            return
        }
        startObserving(event: event)
    }

    private func startObserving(event: BridgeLibraryEvent) {
        let observer = self.makeObserver(for: event)
        eventObservers[event] = observer
        observer.startObserving { [weak self] in
            self?.sendEvent(withName: event.rawValue, body: $0)
        }
    }

    private func makeObserver(for event: BridgeLibraryEvent) -> EventObserver {
        switch event {
        case .resultAvailable: return ResultsObserver()
        }
    }

    override func removeListeners(_ count: Double) {
        super.removeListeners(count)
        eventObservers[.resultAvailable]?.stopObserving()
    }

    override func constantsToExport() -> [AnyHashable : Any]! {
        return constantsProvider.constants
    }

    override class func requiresMainQueueSetup() -> Bool {
        return true
    }

    override var methodQueue: DispatchQueue! {
        DispatchQueue.main
    }

}
