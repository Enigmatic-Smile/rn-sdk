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
    FLSDKOptionDeleteInstructions = 5,
    FLSDKOptionPrivacyURL = 6,
    FLSDKOptionCardSchemes = 7,
    FLSDKOptionUnexistent = NSUIntegerMax
};
