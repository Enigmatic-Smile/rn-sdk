
#import "Fidel.h"
#import "Fidel-Swift.h"
#import "RCTConvert+Country.h"
#import "FLRNOptionsAdapter.h"
#import "FLRNCountryFromJSAdapter.h"
#import "FLRNImageFromRNAdapter.h"

@interface Fidel()

@property (nonatomic, strong) FLRNOptionsAdapter *adapter;

@end

@implementation Fidel

RCT_EXPORT_MODULE()

-(instancetype)init {
    self = [super init];
    if (self) {
        id<FLRNCountryAdapter> countryAdapter = [[FLRNCountryFromJSAdapter alloc] init];
        id<FLRNImageAdapter> imageAdapter = [[FLRNImageFromRNAdapter alloc] init];
        _adapter = [[FLRNOptionsAdapter alloc] initWithCountryAdapter:countryAdapter
                                                         imageAdapter:imageAdapter];
    }
    return self;
}

RCT_EXPORT_METHOD(setOptions:(NSDictionary *)options) {
    [self.adapter setOptions: options];
}

RCT_EXPORT_METHOD(openForm) {
    [FLFidel setApiKey:@"your API key"];
    [FLFidel setProgramId:@"your program id"];
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    [FLFidel present:rootViewController onCardLinkedCallback:nil onCardLinkFailedCallback:nil];
}

-(NSDictionary *)constantsToExport {
    return self.adapter.constantsToExport;
}

-(BOOL)requiresMainQueueSetup {
    return YES;
}

-(dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

@end
