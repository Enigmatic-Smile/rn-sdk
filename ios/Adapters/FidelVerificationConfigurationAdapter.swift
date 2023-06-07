//
//  FidelVerificationConfigurationAdapter.swift
//  fidel-react-native
//
//  Created by Valeria Rogatchevskikh on 07/06/23.
//

import Foundation
import Fidel

class FidelVerificationConfigurationAdapter: NSObject {

    func adapt(_ parameters: NSDictionary) -> CardVerificationConfiguration {
        let id = parameters[CardVerificationConfigurationProperties.id.rawValue] as? String ?? ""
        let consentID = parameters[CardVerificationConfigurationProperties.consentID.rawValue] as? String ?? ""
        let last4Digits = parameters[CardVerificationConfigurationProperties.last4Digits.rawValue] as? String ?? nil
        return CardVerificationConfiguration(cardID: id, consentID: consentID, last4Digits: last4Digits)
    }
}
