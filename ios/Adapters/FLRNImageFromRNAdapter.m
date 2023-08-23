//
//  FLRNImageFromRNAdapter.m
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNImageFromRNAdapter.h"

#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#elif __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import "React/RCTConvert.h" // Required when used as a Pod in a Swift project
#endif

@implementation FLRNImageFromRNAdapter

-(UIImage *)imageFromRawData:(id)rawImageData {
    NSObject *bannerImageOption = rawImageData;
    UIImage *bannerImage = [RCTConvert UIImage:bannerImageOption];
    return bannerImage;
}

@end
