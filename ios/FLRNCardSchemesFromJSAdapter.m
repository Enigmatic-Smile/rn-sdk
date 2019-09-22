//
//  FLRNCardSchemesFromJSAdapter.m
//  Fidel
//
//  Created by Corneliu on 12/05/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNCardSchemesFromJSAdapter.h"
#import "RCTConvert+CardScheme.h"
#if __has_include(<Fidel/Fidel-Swift.h>)
#import <Fidel/Fidel-Swift.h>
#elif __has_include("Fidel-Swift.h")
#import "Fidel-Swift.h"
#else
#import "Fidel/Fidel-Swift.h" // Required when used as a Pod in a Swift project
#endif

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
