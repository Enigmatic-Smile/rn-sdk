//
//  Constants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation

enum JSProperties {
    case setup(SetupJSProperties)
}

enum SetupJSProperties: String {
    case sdkKey
    case programID = "programId"
    case companyName
    case programType
    case options
    case consentText
}
