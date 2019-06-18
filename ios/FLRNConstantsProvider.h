//
//  FLRNConstantsProvider.h
//  Fidel
//
//  Created by Corneliu on 12/05/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol FLRNConstantsProvider <NSObject>

@property (nonatomic, readonly) NSDictionary *constantsToExport;

@end
