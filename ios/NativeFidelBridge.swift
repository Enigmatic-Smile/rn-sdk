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
    
    private let resultsObserver = ResultsObserver()
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
        let cardConfig = verificationConfigAdapter.adapt(parameters)
        Fidel.verifyCard(from: startViewController, cardVerificationConfiguration: cardConfig)
    }

    override func supportedEvents() -> [String]! {
        return ["ResultAvailable"]
    }
    
    override func addListener(_ eventName: String!) {
        super.addListener(eventName)
        resultsObserver.startObserving {[weak self] result in
            self?.sendEvent(withName: "ResultAvailable", body: result)
        }
    }
    
    override func removeListeners(_ count: Double) {
        super.removeListeners(count)
        resultsObserver.stopObserving()
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
