//
//  ImageAdapter.swift
//  fidel-react-native
//
//  Created by Corneliu Chitanu on 08/12/21.
//

import Foundation
import UIKit

@objc(FLRNImageAdapter)
protocol ImageAdapter {
    @objc(imageFromRawData:)
    func image(from rawImageData: NSObject) -> UIImage?
}
