//
//  FLRNCardSchemeAdapter.h
//  Fidel
//
//  Created by Corneliu on 12/05/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FLRNConstantsProvider.h"

@protocol FLRNCardSchemesAdapter <FLRNConstantsProvider>

-(NSSet<NSNumber *> *)cardSchemesWithRawObject:(id)objectToAdapt;

@end
