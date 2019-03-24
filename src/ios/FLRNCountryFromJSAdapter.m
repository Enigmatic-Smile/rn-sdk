//
//  FLRNCountryFromJSAdapter.m
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNCountryFromJSAdapter.h"
#import "RCTConvert+Country.h"

@implementation FLRNCountryFromJSAdapter

- (NSDictionary *)countryConstantsToExport {
    return FLCountryValues;
}

-(FLCountry)adaptedCountry:(id)rawCountry {
    return [RCTConvert FLCountry:rawCountry];
}

@end
