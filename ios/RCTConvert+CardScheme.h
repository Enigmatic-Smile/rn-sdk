//
//  RCTConvert+CardScheme.h
//  Fidel
//
//  Created by Corneliu on 12/05/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RCTConvert.h"
#import "Fidel-Swift.h"

#define FLCardSchemeValues @{@"visa" : @(FLCardSchemeVisa), @"mastercard" : @(FLCardSchemeMastercard), @"americanExpress" : @(FLCardSchemeAmericanExpress)}
#define FLCardSchemeInvalid NSIntegerMax

@implementation RCTConvert (CardScheme)

RCT_ENUM_CONVERTER(FLCardScheme, (FLCardSchemeValues),
                   FLCardSchemeInvalid, integerValue);

@end
