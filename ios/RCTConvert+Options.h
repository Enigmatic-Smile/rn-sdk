//
//  RCTConvert+Option.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

//#import <React/RCTConvert.h>
#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#elif __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import "React/RCTConvert.h" // Required when used as a Pod in a Swift project
#endif

#import "FLRNSDKOptions.h"

#define FLSDKOptionValues\
@{\
    kBannerImageOptionKey: @(FLSDKOptionBannerImage), \
    kCountryOptionKey: @(FLSDKOptionCountry), \
    kAutoScanOptionKey: @(FLSDKOptionAutoScan),\
    kMetaDataOptionKey: @(FLSDKOptionMetaData), \
    kCompanyNameOptionKey: @(FLSDKOptionCompanyName), \
    kProgramNameOptionKey: @(FLSDKOptionProgramName), \
    kDeleteInstructionsOptionKey: @(FLSDKOptionCompanyName), \
    kPrivacyURLOptionKey: @(FLSDKOptionCompanyName), \
    kTermsConditionsURLOptionKey: @(FLSDKOptionTermsConditionsURL), \
    kCardSchemesDataOptionKey: @(FLSDKOptionCardSchemes) \
}

FOUNDATION_EXPORT NSString *const kBannerImageOptionKey;
FOUNDATION_EXPORT NSString *const kCountryOptionKey;
FOUNDATION_EXPORT NSString *const kAutoScanOptionKey;
FOUNDATION_EXPORT NSString *const kMetaDataOptionKey;
FOUNDATION_EXPORT NSString *const kCompanyNameOptionKey;
FOUNDATION_EXPORT NSString *const kProgramNameOptionKey;
FOUNDATION_EXPORT NSString *const kDeleteInstructionsOptionKey;
FOUNDATION_EXPORT NSString *const kPrivacyURLOptionKey;
FOUNDATION_EXPORT NSString *const kTermsConditionsURLOptionKey;
FOUNDATION_EXPORT NSString *const kCardSchemesDataOptionKey;

@implementation RCTConvert (Options)

RCT_ENUM_CONVERTER(FLSDKOption, (FLSDKOptionValues),
                   FLSDKOptionUnexistent, integerValue);

@end
