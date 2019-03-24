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
    kCountryOptionKey: @(FLSDKOptionCountry)\
}

FOUNDATION_EXPORT NSString *const kBannerImageOptionKey;
FOUNDATION_EXPORT NSString *const kCountryOptionKey;

@implementation RCTConvert (Options)

RCT_ENUM_CONVERTER(FLSDKOption, (FLSDKOptionValues),
                   FLSDKOptionUnexistent, integerValue);

@end
