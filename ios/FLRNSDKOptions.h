//
//  FLRNSDKOptions.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSUInteger, FLSDKOption) {
    FLSDKOptionBannerImage = 0,
    FLSDKOptionAllowedCountries = 1,
    FLSDKOptionDefaultSelectedCountry = 2,
    FLSDKOptionAutoScan = 3,
    FLSDKOptionMetaData = 4,
    FLSDKOptionCompanyName = 5,
    FLSDKOptionProgramName = 6,
    FLSDKOptionDeleteInstructions = 7,
    FLSDKOptionPrivacyURL = 8,
    FLSDKOptionTermsConditionsURL = 9,
    FLSDKOptionCardSchemes = 10,
    FLSDKOptionUnexistent = NSUIntegerMax
};
