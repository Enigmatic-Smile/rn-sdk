//
//  CardVerificationStartedObserver.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 07/06/23.
//

import Foundation
import Fidel

struct CardVerificationStartedObserver: EventObserver {
    func startObserving(_ onEvent: @escaping (NSDictionary) -> Void) {
        Fidel.onCardVerificationStarted = {
            onEvent($0.dictionary as NSDictionary)
        }
    }
    
    func stopObserving() {
        Fidel.onCardVerificationStarted = nil
    }
}
