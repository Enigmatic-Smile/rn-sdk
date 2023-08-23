//
//  CountryConstants.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

extension Country: ConstantsProvider {
    
    var constantKey: String {
        switch self {
        case .canada: return "canada"
        case .ireland: return "ireland"
        case .japan: return "japan"
        case .sweden: return "sweden"
        case .unitedArabEmirates: return "unitedArabEmirates"
        case .unitedKingdom: return "unitedKingdom"
        case .unitedStates: return "unitedStates"
        case .norway: return "norway"
        @unknown default: return "unexpected"
        }
    }
    
    static var allCases: Set<Country> = Country.allCountries
    static var parentKeyName: String = String(describing: Self.self)
    
    static func countriesSet(from countryConstantKeys: [String]) -> Set<Country> {
        return Set<Country>(countryConstantKeys.compactMap { Country.country(from: $0) })
    }
    
    static func country(from countryConstantKey: String) -> Country? {
        switch countryConstantKey {
        case "canada": return .canada
        case "ireland": return .ireland
        case "japan": return .japan
        case "sweden": return .sweden
        case "unitedArabEmirates": return .unitedArabEmirates
        case "unitedKingdom": return .unitedKingdom
        case "unitedStates": return .unitedStates
        case "norway": return .norway
        default: return nil
        }
    }
}
