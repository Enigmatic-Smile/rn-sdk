//
//  ResultsObserver.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

struct ResultsObserver: EventObserver {
    
    func startObserving(_ onEvent: @escaping (NSDictionary) -> Void) {
        Fidel.onResult = { result in
            var resultDictionary = [String: Any?]()
            switch result {
            case .enrollmentResult(let enrollmentResult):
                resultDictionary[JSResultProperties.type.rawValue] = JSResultTypes.enrollmentResult.rawValue
                resultDictionary[JSResultProperties.enrollmentResult.rawValue] = enrollmentResult.dictionary
            case .verificationResult(let verificationResult):
                resultDictionary[JSResultProperties.type.rawValue] = JSResultTypes.verificationResult.rawValue
                resultDictionary[JSResultProperties.verificationResult.rawValue] = verificationResult.dictionary
            case .error(let fidelError):
                resultDictionary[JSResultProperties.type.rawValue] = JSResultTypes.error.rawValue
                resultDictionary[JSResultProperties.error.rawValue] = fidelError.dictionary
            @unknown default:
                resultDictionary[JSResultProperties.type.rawValue] = "unexpected"
            }
            let resultNSDictionary = resultDictionary as NSDictionary
            onEvent(resultNSDictionary)
        }
    }
    
    func stopObserving() {
        Fidel.onResult = nil
    }
}
