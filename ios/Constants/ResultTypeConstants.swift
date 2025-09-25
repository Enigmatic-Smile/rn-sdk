//
//  ResultTypeConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension FidelResult: ConstantsProvider {
    static var allCases: Set<FidelResult> = []
    static var parentKeyName: String = "ResultType"

    var constantKey: String {
        switch self {
        case .enrollmentResult: return "EnrollmentResult"
        case .error: return "Error"
        @unknown default: return "unexpected"
        }
    }

    static var constantsToExport: [String : [String : String]] {
        var casesConstants = [String: String]()
        casesConstants["EnrollmentResult"] = "EnrollmentResult"
        casesConstants["Error"] = "Error"
        return [Self.parentKeyName: casesConstants]
    }

    public func hash(into hasher: inout Hasher) {
        hasher.combine(self.constantKey)
    }
}
