//
//  RCTConvert+CardScheme.h
//  Fidel
//
//  Created by Corneliu on 12/05/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

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

#define FLCardSchemeValues @{@"visa" : @(FLCardSchemeVisa), @"mastercard" : @(FLCardSchemeMastercard), @"americanExpress" : @(FLCardSchemeAmericanExpress)}
#define FLCardSchemeInvalid NSIntegerMax

@implementation RCTConvert (CardScheme)

RCT_ENUM_CONVERTER(FLCardScheme, (FLCardSchemeValues),
                   FLCardSchemeInvalid, integerValue);

@end
