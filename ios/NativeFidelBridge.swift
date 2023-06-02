//
//  NativeFidelBridge.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 06/01/22.
//

import Foundation
import React

@objc(NativeFidelBridge)
class NativeFidelBridge: RCTEventEmitter {
    
    private let resultsObserver = ResultsObserver()
    private let imageAdapter = FLRNImageFromRNAdapter()
    private let flowStarter = FlowStarter()
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
        flowStarter.start(from: startViewController)
    }
    
    @objc(verifyCard:)
    func verifyCard(with parameters: NSDictionary) {
        guard let startViewController = UIApplication.shared.delegate?.window??.rootViewController else {
            return
        }
        
        var id = ""
        var consentID = ""
        var last4Digits = ""
        guard let cardConfig = parameters[JSProperties.cardConfig.rawValue] as? [String: Any?]
        else {
            return
        }
        if cardConfig.keys.contains(JSProperties.CardConfig.id.rawValue) {
            id = cardConfig[JSProperties.CardConfig.id.rawValue] as? String ?? ""
        }
        if cardConfig.keys.contains(JSProperties.CardConfig.consentID.rawValue) {
            consentID = cardConfig[JSProperties.CardConfig.consentID.rawValue] as? String ?? ""
        }
        if cardConfig.keys.contains(JSProperties.CardConfig.last4Digits.rawValue) {
            last4Digits = cardConfig[JSProperties.CardConfig.last4Digits.rawValue] as? String ?? ""
        }
        flowStarter.verifyCard(from: startViewController, id: id, consentID: consentID, last4Digits: last4Digits)
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
