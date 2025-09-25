//
//  ProgramTypeConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension ProgramType: ConstantsProvider {

    var constantKey: String {
        switch self {
        case .transactionSelect: return "transactionSelect"
        @unknown default: return "unexpected"
        }
    }

    public static var allCases: Set<ProgramType> = [ProgramType.transactionSelect]
    static var parentKeyName: String = String(describing: Self.self)

    static func programType(from constantKey: String?) -> ProgramType {
        return .transactionSelect
    }
}
