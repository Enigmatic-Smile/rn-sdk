//
//  RCTConvert+Country.h
//  Fidel
//
//  Created by Corneliu on 22/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

//#import "RCTConvert.h"
#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#elif __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import "React/RCTConvert.h" // Required when used as a Pod in a Swift project
#endif

#if __has_include(<Fidel/Fidel-Swift.h>)
#import <Fidel/Fidel-Swift.h>
#elif __has_include("Fidel-Swift.h")
#import "Fidel-Swift.h"
#else
#import "Fidel/Fidel-Swift.h" // Required when used as a Pod in a Swift project
#endif

#define FLCountryValues @{@"unitedKingdom" : @(FLCountryUnitedKingdom), @"ireland" : @(FLCountryIreland), @"unitedStates" : @(FLCountryUnitedStates), @"sweden" : @(FLCountrySweden), @"japan" : @(FLCountryJapan), @"canada" : @(FLCountryCanada), @"noDefault" : @(FLCountryNoDefault)}

@implementation RCTConvert (Country)

RCT_ENUM_CONVERTER(FLCountry, (FLCountryValues),
                   FLCountryNoDefault, integerValue);

@end
