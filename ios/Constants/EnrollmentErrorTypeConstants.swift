//
//  EnrollmentErrorTypeConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension EnrollmentError: ConstantsProvider {
    static var allCases: Set<EnrollmentError> = [EnrollmentError.cardAlreadyExists, .invalidProgramID, .invalidSDKKey, .inexistentProgram, .unexpected]
    static var parentKeyName: String = "EnrollmentErrorType"
    
    var constantKey: String {
        switch self {
        case .cardAlreadyExists: return "cardAlreadyExists"
        case .invalidProgramID: return "invalidProgramId"
        case .invalidSDKKey: return "invalidSdkKey"
        case .inexistentProgram: return "inexistentProgram"
        case .unexpected: return "unexpected"
        @unknown default: return "unexpected"
        }
    }
}
