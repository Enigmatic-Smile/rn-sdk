
#import "Fidel.h"
#import "Fidel-Swift.h"
#import "RCTConvert+Country.h"
#import "FLRNSetupAdapter.h"
#import "FLRNOptionsAdapter.h"
#import "FLRNCountryFromJSAdapter.h"
#import "FLRNImageFromRNAdapter.h"

@interface Fidel()

@property (nonatomic, strong) FLRNOptionsAdapter *adapter;
@property (nonatomic, strong) FLRNSetupAdapter *setupAdapter;

@end

@implementation Fidel

RCT_EXPORT_MODULE()

-(instancetype)init {
    self = [super init];
    if (self) {
        id<FLRNCountryAdapter> countryAdapter;
        countryAdapter = [[FLRNCountryFromJSAdapter alloc] init];
        id<FLRNImageAdapter> imageAdapter = [[FLRNImageFromRNAdapter alloc] init];
        _adapter = [[FLRNOptionsAdapter alloc] initWithCountryAdapter:countryAdapter
                                                         imageAdapter:imageAdapter];
        _setupAdapter = [[FLRNSetupAdapter alloc] init];
    }
    return self;
}

RCT_EXPORT_METHOD(setup:(NSDictionary *)setupInfo) {
    [self.setupAdapter setupWith:setupInfo];
}

RCT_EXPORT_METHOD(setOptions:(NSDictionary *)options) {
    [self.adapter setOptions: options];
}

RCT_EXPORT_METHOD(openForm) {
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    [FLFidel present:rootViewController onCardLinkedCallback:nil onCardLinkFailedCallback:nil];
}

-(NSDictionary *)constantsToExport {
    NSMutableDictionary *constants = [self.setupAdapter.constantsToExport mutableCopy];
    [constants addEntriesFromDictionary:self.adapter.constantsToExport];
    return [constants copy];
}

-(BOOL)requiresMainQueueSetup {
    return YES;
}

-(dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

@end
