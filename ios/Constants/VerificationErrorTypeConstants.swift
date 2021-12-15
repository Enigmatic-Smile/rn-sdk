//
//  VerificationErrorTypeConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension VerificationError: ConstantsProvider {
    static var allCases: Set<VerificationError> = [VerificationError.maximumAttemptsReached, .cardAlreadyVerified, .cardNotFound, .verificationNotFound, .genericError, .incorrectAmount, .invalidSDKKey, .unauthorized, .unexpected]
    static var parentKeyName: String = "VerificationErrorType"
    
    var constantKey: String {
        switch self {
        case .maximumAttemptsReached: return "maximumAttemptsReached"
        case .cardAlreadyVerified: return "cardAlreadyVerified"
        case .invalidSDKKey: return "invalidSdkKey"
        case .cardNotFound: return "cardNotFound"
        case .verificationNotFound: return "verificationNotFound"
        case .genericError: return "genericError"
        case .incorrectAmount: return "incorrectAmount"
        case .unauthorized: return "unauthorized"
        case .unexpected: return "unexpected"
        @unknown default: return "unexpected"
        }
    }
}
