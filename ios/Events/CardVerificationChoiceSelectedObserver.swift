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
            dictionary["CardVerificationChoice"] = $0.constantKey
            onEvent(dictionary)
        }
    }
    
    func stopObserving() {
        Fidel.onCardVerificationChoiceSelected = nil
    }
}
