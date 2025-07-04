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
            case .australia: return "australia"
            case .newZealand: return "newZealand"
            case .hongKong: return "hongKong"
            case .philippines: return "philippines"
            case .singapore: return "singapore"
            case .vietnam: return "vietnam"
            case .switzerland: return "switzerland"
            case .finland: return "finland"
            case .denmark: return "denmark"
            case .brazil: return "brazil"
            case .egypt: return "egypt"
            case .oman: return "oman"
            case .qatar: return "qatar"
            case .bahrain: return "bahrain"
            case .kuwait: return "kuwait"
            case .saudiArabia: return "saudiArabia"
            case .jordan: return "jordan"
            case .southAfrica: return "southAfrica"
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
            case "australia": return .australia
            case "newZealand": return .newZealand
            case "hongKong": return .hongKong
            case "philippines": return .philippines
            case "singapore": return .singapore
            case "vietnam": return .vietnam
            case "switzerland": return .switzerland
            case "finland": return .finland
            case "denmark": return .denmark
            case "brazil": return .brazil
            case "egypt": return .egypt
            case "oman": return .oman
            case "qatar": return .qatar
            case "bahrain": return .bahrain
            case "kuwait": return .kuwait
            case "saudiArabia": return .saudiArabia
            case "jordan": return .jordan
            case "southAfrica": return .southAfrica
            default: return nil
        }
    }
}
