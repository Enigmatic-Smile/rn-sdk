//
//  FLRNCountryAdapter.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FLRNConstantsProvider.h"
#if __has_include(<Fidel/Fidel-Swift.h>)
#import <Fidel/Fidel-Swift.h>
#elif __has_include("Fidel-Swift.h")
#import "Fidel-Swift.h"
#else
#import "Fidel/Fidel-Swift.h" // Required when used as a Pod in a Swift project
#endif

@protocol FLRNCountryAdapter <FLRNConstantsProvider>

-(FLCountry)adaptedCountry:(id)rawCountry;

@end
