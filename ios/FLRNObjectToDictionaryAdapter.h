//
//  FLRNObjectToDictionaryAdapter.h
//  Fidel
//
//  Created by Corneliu on 22/04/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol FLRNObjectToDictionaryAdapter <NSObject>

-(NSDictionary *)dictionaryFrom:(NSObject *)object;

@end
