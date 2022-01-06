//
//  ResultsObserver.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

class ResultsObserver {
    
    private var onResultCallback: ((NSDictionary) -> Void)?
    
    func startObserving(_ callback: @escaping (NSDictionary) -> Void) {
        self.onResultCallback = callback
        Fidel.onResult = self.onResult
    }
    
    func stopObserving() {
        Fidel.onResult = nil
    }
    
    private func onResult(_ result: FidelResult) {
        var resultDictionary = [String: Any?]()
        switch result {
        case .enrollmentResult(let enrollmentResult):
            resultDictionary[JSResultProperties.type.rawValue] = JSResultTypes.enrollmentResult.rawValue
            resultDictionary[JSResultProperties.enrollmentResult.rawValue] = enrollmentResult.dictionary
        case .verificationSuccessful:
            resultDictionary[JSResultProperties.type.rawValue] = JSResultTypes.verificationSuccessful.rawValue
        case .error(let fidelError):
            resultDictionary[JSResultProperties.type.rawValue] = JSResultTypes.error.rawValue
            resultDictionary[JSResultProperties.error.rawValue] = fidelError.dictionary
        @unknown default:
            resultDictionary[JSResultProperties.type.rawValue] = "unexpected"
        }
        let resultNSDictionary = resultDictionary as NSDictionary
        onResultCallback?(resultNSDictionary)
    }
}
