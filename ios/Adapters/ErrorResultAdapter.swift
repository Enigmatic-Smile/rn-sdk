//
//  ErrorResultAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 09/12/21.
//

import Foundation
import Fidel

extension FidelError {
    var dictionary: [String: Any] {
        var dictionaryToReturn: [String: Any] = [
            "type": type.jsTypeValue,
            "message": message,
            "date": date.timeIntervalSince1970
        ]
        if let subtype = type.jsErrorSubtype {
            dictionaryToReturn["subtype"] = subtype
        }
        return dictionaryToReturn
    }
}

extension FidelErrorType {
    var jsTypeValue: String {
        switch self {
        case .enrollmentError: return "enrollmentError"
        case .userCanceled: return "userCanceled"
        case .sdkConfigurationError: return "sdkConfigurationError"
        case .deviceNotSecure: return "deviceNotSecure"
        case .genericError: return "genericError"
        @unknown default: return "unknown"
        }
    }

    var jsErrorSubtype: String? {
        switch self {
        case let .enrollmentError(enrollmentError):
            switch enrollmentError {
            case .cardAlreadyExists: return "cardAlreadyExists"
            case .inexistentProgram: return "inexistentProgram"
            case .invalidSDKKey: return "invalidSdkKey"
            case .invalidProgramID: return "invalidProgramId"
            case .unexpected: return "unexpected"
            case .unauthorized: return "unauthorized"
            case .issuerProcessingError: return "issuerProcessingError"
            case .duplicateTransactionError: return "duplicateTransactionError"
            case .insufficientFundsError: return "insufficientFundsError"
            case .processingChargeError: return "processingChargeError"
            case .cardDetailsError: return "cardDetailsError"
            case .cardLimitExceededError: return "cardLimitExceededError"
            @unknown default: return "unexpected"
            }
        default: return nil
        }
    }
}
