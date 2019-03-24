//
//  FLRNOptionsAdapter.m
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNOptionsAdapter.h"
#import "Fidel-Swift.h"
#import "FLRNCountryAdapter.h"
#import "FLRNImageAdapter.h"

@interface FLRNOptionsAdapter()

@property (nonatomic, strong) id<FLRNCountryAdapter> countryAdapter;
@property (nonatomic, strong) id<FLRNImageAdapter> imageAdapter;

@end

@implementation FLRNOptionsAdapter

- (instancetype)initWithCountryAdapter:(id<FLRNCountryAdapter>)countryAdapter
                          imageAdapter:(id<FLRNImageAdapter>)imageAdapter {
    self = [super init];
    if (self) {
        _countryAdapter = countryAdapter;
        _imageAdapter = imageAdapter;
    }
    return self;
}

-(NSDictionary *)constantsToExport {
    return @{@"Country": self.countryAdapter.countryConstantsToExport};
}

- (void)setOptions:(NSDictionary *)options {
    id rawBannerData = options[@"bannerImage"];
    UIImage *bannerImage = [self.imageAdapter imageFromRawData:rawBannerData];
    [FLFidel setBannerImage:bannerImage];
    
    id rawCountry = options[@"country"];
    FLFidel.country = [self.countryAdapter adaptedCountry:rawCountry];
}

@end
