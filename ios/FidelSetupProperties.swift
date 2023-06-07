//
//  FidelSetupProperties.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation

enum FidelSetupProperties: String {
    case sdkKey
    case programID = "programId"
    case programType
    case options
    case consentText
    
    enum Options: String {
        case bannerImage
        case allowedCountries
        case defaultSelectedCountry
        case supportedCardSchemes
        case shouldAutoScanCard
        case enableCardScanner
        case thirdPartyVerificationChoice
        case metaData
    }
    
    enum ConsentText: String {
        case termsAndConditionsURL = "termsAndConditionsUrl"
        case privacyPolicyURL = "privacyPolicyUrl"
        case companyName
        case programName
        case deleteInstructions
    }
}
