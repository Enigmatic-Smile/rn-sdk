//
//  FLRNCountryAdapter.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FLRNConstantsProvider.h"
#import "Fidel-Swift.h"

@protocol FLRNCountryAdapter <FLRNConstantsProvider>

-(FLCountry)adaptedCountry:(id)rawCountry;

@end
