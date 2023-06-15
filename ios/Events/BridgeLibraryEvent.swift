//
//  BridgeLibraryEvent.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 07/06/23.
//

import Foundation

enum BridgeLibraryEvent: String, CaseIterable {
    case resultAvailable = "ResultAvailable"
    case cardVerificationStarted = "CardVerificationStarted"
    case cardVerificationChoiceSelected = "CardVerificationChoiceSelected"
}
