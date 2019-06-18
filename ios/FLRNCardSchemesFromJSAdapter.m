//
//  FLRNCardSchemesFromJSAdapter.m
//  Fidel
//
//  Created by Corneliu on 12/05/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNCardSchemesFromJSAdapter.h"
#import "RCTConvert+CardScheme.h"
#import "Fidel-Swift.h"

@implementation FLRNCardSchemesFromJSAdapter

-(NSSet<NSNumber *> *)cardSchemesWithRawObject:(id)objectToAdapt {
    NSMutableSet<NSNumber *> *setToReturn;
    if ([objectToAdapt isKindOfClass:[NSArray class]]) {
        NSArray *arrayToAdapt = (NSArray *)objectToAdapt;
        setToReturn = [NSMutableSet set];
        for (id objectToAdapt in arrayToAdapt) {
            FLCardScheme cardScheme = [RCTConvert FLCardScheme:objectToAdapt];
            [setToReturn addObject:@(cardScheme)];
        }
    }
    return setToReturn;
}

-(NSDictionary *)constantsToExport {
    return FLCardSchemeValues;
}


@end
