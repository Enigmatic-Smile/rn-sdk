//
//  RCTConvert+Option.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RCTConvert.h"
#import "FLRNSDKOptions.h"

#define FLSDKOptionValues\
@{\
    kBannerImageOptionKey: @(FLSDKOptionBannerImage), \
    kCountryOptionKey: @(FLSDKOptionCountry), \
    kAutoScanOptionKey: @(FLSDKOptionAutoScan),\
    kMetaDataOptionKey: @(FLSDKOptionMetaData) \
}

FOUNDATION_EXPORT NSString *const kBannerImageOptionKey;
FOUNDATION_EXPORT NSString *const kCountryOptionKey;
FOUNDATION_EXPORT NSString *const kAutoScanOptionKey;
FOUNDATION_EXPORT NSString *const kMetaDataOptionKey;

@implementation RCTConvert (Options)

RCT_ENUM_CONVERTER(FLSDKOption, (FLSDKOptionValues),
                   FLSDKOptionUnexistent, integerValue);

@end
