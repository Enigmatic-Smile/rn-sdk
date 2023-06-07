//
//  ConsentDetailsAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 07/06/23.
//

import Foundation
import Fidel

extension ConsentDetails {
    var dictionary: [String: Any] {
        return [
            "cardId": cardID,
            "consentId": consentID,
            "programId": programID
        ]
    }
}
