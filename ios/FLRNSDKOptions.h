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
    FLSDKOptionCountry = 1,
    FLSDKOptionAutoScan = 2,
    FLSDKOptionMetaData = 3,
    FLSDKOptionCompanyName = 4,
    FLSDKOptionProgramName = 5,
    FLSDKOptionDeleteInstructions = 6,
    FLSDKOptionPrivacyURL = 7,
    FLSDKOptionTermsConditionsURL = 8,
    FLSDKOptionCardSchemes = 9,
    FLSDKOptionUnexistent = NSUIntegerMax
};
