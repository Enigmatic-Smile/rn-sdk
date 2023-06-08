//
//  CardVerificationChoiceSelectedObserver.swift
//  fidel-react-native
//
//  Created by Valeria Rogatchevskikh on 8/6/23.
//

import Foundation

import Foundation
import Fidel

struct CardVerificationChoiceSelectedObserver: EventObserver {
    func startObserving(_ onEvent: @escaping (NSDictionary) -> Void) {
        Fidel.onCardVerificationChoiceSelected = {
            let dictionary: NSMutableDictionary = NSMutableDictionary()
            switch($0) {
            case .delegatedToThirdParty:
                dictionary["verificationChoice"] = "ON_THE_SPOT"
            case .onTheSpot:
                dictionary["verificationChoice"] = "DELEGATED_TO_THIRD_PARTY"
            }
            onEvent(dictionary)
        }
    }
    
    func stopObserving() {
        Fidel.onCardVerificationChoiceSelected = nil
    }
}
