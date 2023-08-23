//
//  NativeFidelBridge.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 06/01/22.
//

import Foundation
import React
import Fidel

@objc(NativeFidelBridge)
class NativeFidelBridge: RCTEventEmitter {
    
    private var eventObservers = [BridgeLibraryEvent: EventObserver]()
    private let imageAdapter = FLRNImageFromRNAdapter()
    private let setupAdapter: FidelSetupAdapter
    private let constantsProvider = ExportedConstantsProvider()
    private let verificationConfigAdapter = FidelVerificationConfigurationAdapter()
    
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
    
    @objc(verifyCard:)
    func verifyCard(with parameters: NSDictionary) {
        guard let startViewController = UIApplication.shared.delegate?.window??.rootViewController else {
            return
        }
        let cardVerificationConfig = verificationConfigAdapter.adapt(parameters)
        Fidel.verifyCard(from: startViewController, cardVerificationConfiguration: cardVerificationConfig)
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
        case .cardVerificationStarted: return CardVerificationStartedObserver()
        case .resultAvailable: return ResultsObserver()
        case .cardVerificationChoiceSelected: return CardVerificationChoiceSelectedObserver()
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
