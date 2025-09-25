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
        if parameters.keys.contains(FidelSetupProperties.sdkKey.rawValue) {
            Fidel.sdkKey = parameters[FidelSetupProperties.sdkKey.rawValue] as? String
        }
        if parameters.keys.contains(FidelSetupProperties.programID.rawValue) {
            Fidel.programID = parameters[FidelSetupProperties.programID.rawValue] as? String
        }
        if parameters.keys.contains(FidelSetupProperties.programType.rawValue) {
            let programTypeJSValue = parameters[FidelSetupProperties.programType.rawValue] as? String
            Fidel.programType = ProgramType.programType(from: programTypeJSValue)
        }
        if parameters.keys.contains(FidelSetupProperties.options.rawValue),
           let options = parameters[FidelSetupProperties.options.rawValue] as? [String: Any?] {
            setOptions(options)
        }
        if parameters.keys.contains(FidelSetupProperties.consentText.rawValue),
           let consentTextValues = parameters[FidelSetupProperties.consentText.rawValue] as? [String: Any?] {
            setConsentTextValues(consentTextValues)
        }
    }

    func setOptions(_ options: [String: Any?]) {
        if options.keys.contains(FidelSetupProperties.Options.bannerImage.rawValue),
           let rawImageObject = options[FidelSetupProperties.Options.bannerImage.rawValue] as? NSObject {
            Fidel.bannerImage = imageAdapter.image(fromRawData: rawImageObject)
        }
        if options.keys.contains(FidelSetupProperties.Options.allowedCountries.rawValue),
           let countryJSKeys = options[FidelSetupProperties.Options.allowedCountries.rawValue] as? [String] {
            Fidel.allowedCountries = Country.countriesSet(from: countryJSKeys)
        }
        if options.keys.contains(FidelSetupProperties.Options.defaultSelectedCountry.rawValue),
           let countryJSKey = options[FidelSetupProperties.Options.defaultSelectedCountry.rawValue] as? String,
           let defaultSelectedCountry = Country.country(from: countryJSKey) {
            Fidel.defaultSelectedCountry = defaultSelectedCountry
        }
        if options.keys.contains(FidelSetupProperties.Options.supportedCardSchemes.rawValue),
           let supportedCardSchemeJSKeys = options[FidelSetupProperties.Options.supportedCardSchemes.rawValue] as? [String] {
            Fidel.supportedCardSchemes = CardScheme.cardSchemesSet(from: supportedCardSchemeJSKeys)
        }
        if options.keys.contains(FidelSetupProperties.Options.metaData.rawValue) {
            Fidel.metaData = options[FidelSetupProperties.Options.metaData.rawValue] as? [String: Any]
        }
    }

    private func setConsentTextValues(_ consentTextValues: [String: Any?]) {
        if consentTextValues.keys.contains(FidelSetupProperties.ConsentText.companyName.rawValue) {
            Fidel.companyName = consentTextValues[FidelSetupProperties.ConsentText.companyName.rawValue] as? String
        }
        if consentTextValues.keys.contains(FidelSetupProperties.ConsentText.termsAndConditionsURL.rawValue) {
            Fidel.termsAndConditionsURL = consentTextValues[FidelSetupProperties.ConsentText.termsAndConditionsURL.rawValue] as? String
        }
        if consentTextValues.keys.contains(FidelSetupProperties.ConsentText.privacyPolicyURL.rawValue) {
            Fidel.privacyPolicyURL = consentTextValues[FidelSetupProperties.ConsentText.privacyPolicyURL.rawValue] as? String
        }
        if consentTextValues.keys.contains(FidelSetupProperties.ConsentText.programName.rawValue),
           let programName = consentTextValues[FidelSetupProperties.ConsentText.programName.rawValue] as? String {
            Fidel.programName = programName
        }
        if consentTextValues.keys.contains(FidelSetupProperties.ConsentText.deleteInstructions.rawValue),
           let deleteInstructions = consentTextValues[FidelSetupProperties.ConsentText.deleteInstructions.rawValue] as? String {
            Fidel.deleteInstructions = deleteInstructions
        }
    }
}
