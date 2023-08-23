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
        case .transactionStream: return "transactionStream"
        @unknown default: return "unexpected"
        }
    }
    
    public static var allCases: Set<ProgramType> = [ProgramType.transactionSelect, .transactionStream]
    static var parentKeyName: String = String(describing: Self.self)
    
    static func programType(from constantKey: String?) -> ProgramType {
        if constantKey == "transactionStream" {
            return .transactionStream
        }
        return .transactionSelect
    }
}
