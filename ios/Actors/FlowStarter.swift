//
//  FlowStarter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import Fidel

class FlowStarter: NSObject {
    func start(from viewController: UIViewController) {
        Fidel.start(from: viewController)
    }
}
