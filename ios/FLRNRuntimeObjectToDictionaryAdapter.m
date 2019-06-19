//
//  FLRNRuntimeObjectToDictionaryAdapter.m
//  Fidel
//
//  Created by Corneliu on 22/04/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNRuntimeObjectToDictionaryAdapter.h"
#import <objc/runtime.h>

@implementation FLRNRuntimeObjectToDictionaryAdapter

-(NSDictionary *)dictionaryFrom:(NSObject *)object {
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    unsigned count;
    objc_property_t *properties = class_copyPropertyList([object class], &count);
    
    for (int i = 0; i < count; i++) {
        NSString *key = [NSString stringWithUTF8String:property_getName(properties[i])];
        id value = [object valueForKey:key];
        if (value == nil) {
            [dict setObject:[NSNull null] forKey:key];
        }
        else {
            [dict setObject:value forKey:key];
        }
    }
    
    free(properties);
    
    return [NSDictionary dictionaryWithDictionary:dict];
}

@end
