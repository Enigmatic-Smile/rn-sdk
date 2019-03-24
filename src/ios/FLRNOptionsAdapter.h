//
//  FLRNOptionsAdapter.h
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol FLRNCountryAdapter;
@protocol FLRNImageAdapter;

@interface FLRNOptionsAdapter : NSObject

@property (nonatomic, readonly) NSDictionary *constantsToExport;

-(instancetype)initWithCountryAdapter:(id<FLRNCountryAdapter>)countryAdapter
                         imageAdapter:(id<FLRNImageAdapter>)imageAdapter;

-(void)setOptions: (NSDictionary *)options;

@end
