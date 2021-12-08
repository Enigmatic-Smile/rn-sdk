//
//  FidelSetupAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

@objc(FLRNSetupAdapter)
class FidelSetupAdapter: NSObject {
    
    @objc(initWithImageAdapter:)
    init(imageAdapter: ImageAdapter) {
        super.init()
    }
    
    @objc(setupWith:)
    func setup(with parameters: NSDictionary) {
        Fidel.sdkKey = "pk_test_asdasd"
        Fidel.programID = "asdomasd"
        Fidel.termsAndConditionsURL = "https://fidel.uk"
        Fidel.companyName = "some company name"
    }
}
