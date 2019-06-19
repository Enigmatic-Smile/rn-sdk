//
//  FLRNSetupAdapter.h
//  Fidel
//
//  Created by Corneliu on 22/04/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FLRNSetupAdapter : NSObject

@property (nonatomic, readonly) NSDictionary *constantsToExport;

-(void)setupWith: (NSDictionary *)setupInfo;

@end
