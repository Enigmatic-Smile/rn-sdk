//
//  CardVerificationChoiceConstants.swift
//  fidel-react-native
//
//  Created by Valeria Rogatchevskikh on 12/6/23.
//

import Foundation
import Fidel

extension CardVerificationChoice: ConstantsProvider {
    
    var constantKey: String {
        switch self {
        case .onTheSpot: return "onTheSpot"
        case .delegatedToThirdParty: return "delegatedToThirdParty"
        @unknown default: return "unexpected"
        }
    }
    
    public static var allCases: Set<CardVerificationChoice> = [CardVerificationChoice.onTheSpot, .delegatedToThirdParty]
    static var parentKeyName: String = String(describing: Self.self)
    
    static func verificationChoice(from constantKey: String?) -> CardVerificationChoice? {
        switch constantKey {
        case "onTheSpot": return .onTheSpot
        case "delegatedToThirdParty": return .delegatedToThirdParty
        default: return nil
        }
    }
}
