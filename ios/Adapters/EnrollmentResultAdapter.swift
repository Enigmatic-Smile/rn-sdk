//
//  EnrollmentResultAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 09/12/21.
//

import Foundation
import Fidel

extension EnrollmentResult{
    var dictionary: [String: Any] {
        return [
            "cardId": cardID,
            "accountId": accountID,
            "programId": programID,
            "enrollmentDate": enrollmentDate.timeIntervalSince1970,
            "cardScheme": scheme.constantKey,
            "isLive": isLive,
            "cardExpirationYear": cardExpirationYear,
            "cardExpirationMonth": cardExpirationMonth,
            "cardIssuingCountry": cardIssuingCountry.constantKey,
            "cardFirstNumbers": cardFirstNumbers ?? NSNull(),
            "cardLastNumbers": cardLastNumbers ?? NSNull(),
            "metaData": metaData ?? NSNull(),
        ]
    }
}
