//
//  CardSchemeConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension CardScheme: ConstantsProvider {
    
    var constantKey: String {
        switch self {
        case .americanExpress: return "americanExpress"
        case .visa: return "visa"
        case .mastercard: return "mastercard"
        @unknown default: return "unexpected"
        }
    }
    
    static var allCases: Set<CardScheme> = [CardScheme.visa, .mastercard, .americanExpress]
    static var parentKeyName: String = String(describing: Self.self)
}
