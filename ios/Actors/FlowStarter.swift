//
//  FlowStarter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

class FlowStarter: NSObject {
    func start(from viewController: UIViewController) {
        Fidel.start(from: viewController)
    }
    
    func verifyCard(from viewController: UIViewController, id: String, consentID: String, last4Digits: String) {
        let cardConfig = CardVerificationConfiguration(cardID: id, consentID: consentID, last4Digits: last4Digits)
        Fidel.verifyCard(from: viewController, cardVerificationConfiguration: cardConfig)
    }
}
