//
//  FidelSetupAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

class FidelSetupAdapter: NSObject {
    
    private let imageAdapter: FLRNImageFromRNAdapter
    
    init(imageAdapter: FLRNImageFromRNAdapter) {
        self.imageAdapter = imageAdapter
        super.init()
    }
    
    func setup(with parameters: NSDictionary) {
        guard let parameters = parameters as? [String: Any?] else {
            return
        }
        if parameters.keys.contains(JSProperties.sdkKey.rawValue) {
            Fidel.sdkKey = parameters[JSProperties.sdkKey.rawValue] as? String
        }
        if parameters.keys.contains(JSProperties.programID.rawValue) {
            Fidel.programID = parameters[JSProperties.programID.rawValue] as? String
        }
        if parameters.keys.contains(JSProperties.programType.rawValue) {
            let programTypeJSValue = parameters[JSProperties.programType.rawValue] as? String
            Fidel.programType = ProgramType.programType(from: programTypeJSValue)
        }
        if parameters.keys.contains(JSProperties.options.rawValue),
           let options = parameters[JSProperties.options.rawValue] as? [String: Any?] {
            setOptions(options)
        }
        if parameters.keys.contains(JSProperties.consentText.rawValue),
           let consentTextValues = parameters[JSProperties.consentText.rawValue] as? [String: Any?] {
            setConsentTextValues(consentTextValues)
        }
    }
    
    func setOptions(_ options: [String: Any?]) {     
        if options.keys.contains(JSProperties.Options.bannerImage.rawValue),
           let rawImageObject = options[JSProperties.Options.bannerImage.rawValue] as? NSObject {
            Fidel.bannerImage = imageAdapter.image(fromRawData: rawImageObject)
        }
        if options.keys.contains(JSProperties.Options.allowedCountries.rawValue),
           let countryJSKeys = options[JSProperties.Options.allowedCountries.rawValue] as? [String] {
            Fidel.allowedCountries = Country.countriesSet(from: countryJSKeys)
        }
        if options.keys.contains(JSProperties.Options.defaultSelectedCountry.rawValue),
           let countryJSKey = options[JSProperties.Options.defaultSelectedCountry.rawValue] as? String,
           let defaultSelectedCountry = Country.country(from: countryJSKey) {
            Fidel.defaultSelectedCountry = defaultSelectedCountry
        }
        if options.keys.contains(JSProperties.Options.supportedCardSchemes.rawValue),
           let supportedCardSchemeJSKeys = options[JSProperties.Options.supportedCardSchemes.rawValue] as? [String] {
            Fidel.supportedCardSchemes = CardScheme.cardSchemesSet(from: supportedCardSchemeJSKeys)
        }
        if options.keys.contains(JSProperties.Options.shouldAutoScanCard.rawValue) {
            Fidel.shouldAutoScanCard = options[JSProperties.Options.shouldAutoScanCard.rawValue] as? Bool ?? false
        }
        if options.keys.contains(JSProperties.Options.enableCardScanner.rawValue) {
            Fidel.enableCardScanner = options[JSProperties.Options.enableCardScanner.rawValue] as? Bool ?? false
        }
        if options.keys.contains(JSProperties.Options.metaData.rawValue) {
            Fidel.metaData = options[JSProperties.Options.metaData.rawValue] as? [String: Any]
        }
    }
    
    private func setConsentTextValues(_ consentTextValues: [String: Any?]) {
        if consentTextValues.keys.contains(JSProperties.ConsentText.companyName.rawValue) {
            Fidel.companyName = consentTextValues[JSProperties.ConsentText.companyName.rawValue] as? String
        }
        if consentTextValues.keys.contains(JSProperties.ConsentText.termsAndConditionsURL.rawValue) {
            Fidel.termsAndConditionsURL = consentTextValues[JSProperties.ConsentText.termsAndConditionsURL.rawValue] as? String
        }
        if consentTextValues.keys.contains(JSProperties.ConsentText.privacyPolicyURL.rawValue) {
            Fidel.privacyPolicyURL = consentTextValues[JSProperties.ConsentText.privacyPolicyURL.rawValue] as? String
        }
        if consentTextValues.keys.contains(JSProperties.ConsentText.programName.rawValue),
           let programName = consentTextValues[JSProperties.ConsentText.programName.rawValue] as? String {
            Fidel.programName = programName
        }
        if consentTextValues.keys.contains(JSProperties.ConsentText.deleteInstructions.rawValue),
           let deleteInstructions = consentTextValues[JSProperties.ConsentText.deleteInstructions.rawValue] as? String {
            Fidel.deleteInstructions = deleteInstructions
        }
    }
}
