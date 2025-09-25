//
//  ExportedConstantsProvider.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

class ExportedConstantsProvider: NSObject {

    var constants: [AnyHashable: Any] {
        var constants = [String: [String: String]]()
        constants.merge(CardScheme.constantsToExport) { (current, _) in current }
        constants.merge(Country.constantsToExport) { (current, _) in current }
        constants.merge(ProgramType.constantsToExport) { (current, _) in current }
        constants.merge(EnrollmentError.constantsToExport) { (current, _) in current }
        constants.merge(FidelErrorType.constantsToExport) { (current, _) in current }
        constants.merge(FidelResult.constantsToExport) { (current, _) in current }
        return constants
    }
}

protocol ConstantsProvider where Self: Hashable {
    var constantKey: String { get }
    static var allCases: Set<Self> { get }
    static var parentKeyName: String { get }
    static var constantsToExport: [String: [String: String]] { get }
}

extension ConstantsProvider {
    static var constantsToExport: [String: [String: String]] {
        var casesConstants = [String: String]()
        Self.allCases.forEach {
            casesConstants[$0.constantKey] = $0.constantKey
        }
        return [Self.parentKeyName: casesConstants]
    }
}
