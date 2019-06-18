//
//  FLRNImageAdapter.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@protocol FLRNImageAdapter <NSObject>

-(UIImage *)imageFromRawData:(id)rawImageData;

@end
