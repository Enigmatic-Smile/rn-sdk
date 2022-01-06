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
        case .verificationError: return "verificationError"
        case .userCanceled: return "userCanceled"
        case .sdkConfigurationError: return "sdkConfigurationError"
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
            @unknown default: return "unexpected"
            }
        case let .verificationError(verificationError):
            switch verificationError {
            case .cardAlreadyVerified: return "cardAlreadyVerified"
            case .cardNotFound: return "cardNotFound"
            case .verificationNotFound: return "verificationNotFound"
            case .genericError: return "genericError"
            case .maximumAttemptsReached: return "maximumAttemptsReached"
            case .incorrectAmount: return "incorrectAmount"
            case .unauthorized: return "unauthorized"
            case .unexpected: return "unexpected"
            case .invalidSDKKey: return "invalidSdkKey"
            @unknown default: return "unexpected"
            }
        default: return nil
        }
    }
}
