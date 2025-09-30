//
//  ErrorTypeConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension FidelErrorType: @retroactive Hashable {}
extension FidelErrorType: ConstantsProvider {

    static var allCases: Set<FidelErrorType> = [FidelErrorType.userCanceled, .enrollmentError(EnrollmentError.unexpected), .sdkConfigurationError, .genericError]
    static var parentKeyName: String = "ErrorType"

    var constantKey: String {
        switch self {
        case .enrollmentError: return "enrollmentError"
        case .sdkConfigurationError: return "sdkConfigurationError"
        case .userCanceled: return "userCanceled"
        case .deviceNotSecure: return "deviceNotSecure"
        case .genericError: return "genericError"
        @unknown default: return "unexpected"
        }
    }

    public func hash(into hasher: inout Hasher) {
        hasher.combine(self.constantKey)
        switch self {
        case let .enrollmentError(enrollmentError):
            hasher.combine(enrollmentError)
        default: break;
        }
    }
}
