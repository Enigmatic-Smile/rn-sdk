//
//  FLRNOptionsAdapter.m
//  Fidel
//
//  Created by Corneliu on 24/03/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "FLRNOptionsAdapter.h"
#if __has_include(<Fidel/Fidel-Swift.h>)
#import <Fidel/Fidel-Swift.h>
#elif __has_include("Fidel-Swift.h")
#import "Fidel-Swift.h"
#else
#import "Fidel/Fidel-Swift.h" // Required when used as a Pod in a Swift project
#endif
#import "FLRNImageAdapter.h"
#import "FLRNCardSchemesAdapter.h"
#import "RCTConvert+Options.h"

#define FLCountryValues @{@"unitedKingdom" : @(FLCountryUnitedKingdom), @"ireland" : @(FLCountryIreland), @"unitedStates" : @(FLCountryUnitedStates), @"sweden" : @(FLCountrySweden), @"japan" : @(FLCountryJapan), @"canada" : @(FLCountryCanada)}

@interface FLRNOptionsAdapter()

@property (nonatomic, strong) id<FLRNImageAdapter> imageAdapter;
@property (nonatomic, strong) id<FLRNCardSchemesAdapter> cardSchemesAdapter;

@end

@implementation FLRNOptionsAdapter

- (instancetype)initWithImageAdapter:(id<FLRNImageAdapter>)imageAdapter
                    cardSchemesAdapter:(id<FLRNCardSchemesAdapter>)cardSchemesAdapter {
    self = [super init];
    if (self) {
        _imageAdapter = imageAdapter;
        _cardSchemesAdapter = cardSchemesAdapter;
    }
    return self;
}

NSString *const kCountryKey = @"Country";
NSString *const kCardSchemeKey = @"CardScheme";
NSString *const kOptionKey = @"Option";
-(NSDictionary *)constantsToExport {
    return @{
             kCountryKey: FLCountryValues,
             kCardSchemeKey: self.cardSchemesAdapter.constantsToExport,
             kOptionKey: FLSDKOptionValues
             };
}

- (void)setOptions:(NSDictionary *)options {
    if ([self valueIsValidFor:kBannerImageOptionKey fromDictionary:options]) {
        id rawBannerData = options[kBannerImageOptionKey];
        UIImage *bannerImage = [self.imageAdapter imageFromRawData:rawBannerData];
        [FLFidel setBannerImage:bannerImage];
    }
    
    if ([self valueIsValidFor:kAllowedCountriesOptionKey fromDictionary:options]) {
        id rawData = options[kAllowedCountriesOptionKey];
        FLFidel.objc_allowedCountries = (NSArray<NSNumber *> *) rawData;
    }
    
    if ([self valueIsValidFor:kAutoScanOptionKey fromDictionary:options]) {
        FLFidel.autoScan = [options[kAutoScanOptionKey] boolValue];
    }
    
    if ([self valueIsValidFor:kMetaDataOptionKey fromDictionary:options]) {
        id rawMetaData = options[kMetaDataOptionKey];
        if ([rawMetaData isKindOfClass:[NSDictionary class]]) {
            FLFidel.metaData = rawMetaData;
        }
    }
    if([self valueIsValidFor:kCardSchemesDataOptionKey fromDictionary:options]) {
        id rawData = options[kCardSchemesDataOptionKey];
        NSSet<NSNumber *> *supportedCardSchemes = [self.cardSchemesAdapter cardSchemesWithRawObject:rawData];
        FLFidel.objc_supportedCardSchemes = supportedCardSchemes;
    }
    
    if([self valueIsValidFor:kCompanyNameOptionKey fromDictionary:options]) {
        FLFidel.companyName = [self getStringValueFor:kCompanyNameOptionKey fromDictionary:options];
    }
    
    if([self valueIsValidFor:kProgramNameOptionKey fromDictionary:options]) {
        FLFidel.programName = [self getStringValueFor:kProgramNameOptionKey fromDictionary:options];
    }
    
    if([self valueIsValidFor:kDeleteInstructionsOptionKey fromDictionary:options]) {
        FLFidel.deleteInstructions = [self getStringValueFor:kDeleteInstructionsOptionKey fromDictionary:options];
    }
    
    if([self valueIsValidFor:kPrivacyURLOptionKey fromDictionary:options]) {
        FLFidel.privacyURL = [self getStringValueFor:kPrivacyURLOptionKey fromDictionary:options];
    }
    
    if([self valueIsValidFor:kTermsConditionsURLOptionKey fromDictionary:options]) {
        FLFidel.termsConditionsURL = [self getStringValueFor:kTermsConditionsURLOptionKey fromDictionary:options];
    }
}

- (NSString * _Nullable)getStringValueFor:(NSString *)key fromDictionary:(NSDictionary *)dict {
    NSArray *allKeys = dict.allKeys;
    if ([allKeys containsObject:key]) {
        id value = dict[key];
        if ([value isKindOfClass:[NSString class]]) {
            return value;
        }
        else {
            return [value stringValue];
        }
    }
    return nil;
}

- (BOOL)valueIsValidFor:(NSString *)key fromDictionary:(NSDictionary *)dict {
    NSArray *allKeys = dict.allKeys;
    id value = dict[key];
    if ([allKeys containsObject:key]) {
        if ([value isKindOfClass:[NSString class]]) {
            return [allKeys containsObject:key] && (value != nil) && ![value isKindOfClass:[NSNull class]] && [value length] > 0;
        }
        return [allKeys containsObject:key] && (value != nil) && ![value isKindOfClass:[NSNull class]];
    }
    return false;
}

@end
