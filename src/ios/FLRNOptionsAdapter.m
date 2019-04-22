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
#import "RCTConvert+Options.h"

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

NSString *const kCountryKey = @"Country";
NSString *const kOptionKey = @"Option";
-(NSDictionary *)constantsToExport {
    return @{
             kCountryKey: self.countryAdapter.countryConstantsToExport,
             kOptionKey: FLSDKOptionValues
             };
}

- (void)setOptions:(NSDictionary *)options {
    NSArray *allOptionKeys = options.allKeys;
    if ([allOptionKeys containsObject:kBannerImageOptionKey]) {
        id rawBannerData = options[kBannerImageOptionKey];
        UIImage *bannerImage = [self.imageAdapter imageFromRawData:rawBannerData];
        [FLFidel setBannerImage:bannerImage];
    }
    
    if ([allOptionKeys containsObject:kCountryOptionKey]) {
        id rawCountry = options[kCountryOptionKey];
        FLFidel.country = [self.countryAdapter adaptedCountry:rawCountry];
    }
    
    if ([allOptionKeys containsObject:kAutoScanOptionKey]) {
        FLFidel.autoScan = [options[kAutoScanOptionKey] boolValue];
    }
    
    if ([allOptionKeys containsObject:kMetaDataOptionKey]) {
        id rawMetaData = options[kMetaDataOptionKey];
        if ([rawMetaData isKindOfClass:[NSDictionary class]]) {
            FLFidel.metaData = rawMetaData;
        }
    }
}

@end
