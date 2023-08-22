//
//  CardSchemesAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

protocol CardSchemesAdapter {
    func cardSchemes(from rawObject: NSObject) -> Set<CardScheme>
}
