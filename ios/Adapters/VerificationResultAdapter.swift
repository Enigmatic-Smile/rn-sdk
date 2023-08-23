//
//  VerificationResultAdapter.swift
//  fidel-react-native
//
//  Created by Gabriel Godoi on 06/10/22.
//

import Foundation
import Fidel

extension VerificationResult{
    var dictionary: [String: Any] {
        return [
            "cardId": cardID,
        ]
    }
}
