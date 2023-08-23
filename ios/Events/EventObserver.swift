//
//  EventObserver.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 07/06/23.
//

import Foundation

protocol EventObserver {
    func startObserving(_ onEvent: @escaping (NSDictionary) -> Void)
    func stopObserving()
}
