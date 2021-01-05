
#import "Fidel.h"
#if __has_include(<Fidel/Fidel-Swift.h>)
#import <Fidel/Fidel-Swift.h>
#elif __has_include("Fidel-Swift.h")
#import "Fidel-Swift.h"
#else
#import "Fidel/Fidel-Swift.h" // Required when used as a Pod in a Swift project
#endif
#import "FLRNSetupAdapter.h"
#import "FLRNOptionsAdapter.h"
#import "FLRNImageFromRNAdapter.h"
#import "FLRNCardSchemesFromJSAdapter.h"
#import "FLRNRuntimeObjectToDictionaryAdapter.h"

@interface Fidel()

@property (nonatomic, strong) FLRNOptionsAdapter *adapter;
@property (nonatomic, strong) FLRNSetupAdapter *setupAdapter;
@property (nonatomic, strong) id<FLRNObjectToDictionaryAdapter> objectAdapter;

@end

@implementation Fidel

RCT_EXPORT_MODULE(NativeFidelBridge)

-(instancetype)init {
    self = [super init];
    if (self) {
        id<FLRNImageAdapter> imageAdapter = [[FLRNImageFromRNAdapter alloc] init];
        id<FLRNCardSchemesAdapter> cardSchemesAdapter = [[FLRNCardSchemesFromJSAdapter alloc] init];
        _adapter = [[FLRNOptionsAdapter alloc] initWithImageAdapter:imageAdapter
                                                   cardSchemesAdapter:cardSchemesAdapter];
        _setupAdapter = [[FLRNSetupAdapter alloc] init];
        _objectAdapter = [[FLRNRuntimeObjectToDictionaryAdapter alloc] init];
    }
    return self;
}

RCT_EXPORT_METHOD(setup:(NSDictionary *)setupInfo) {
    [self.setupAdapter setupWith:setupInfo];
}

RCT_EXPORT_METHOD(setOptions:(NSDictionary *)options) {
    [self.adapter setOptions: options];
}

RCT_EXPORT_METHOD(openForm:(RCTResponseSenderBlock)callback) {
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    __weak typeof(self) weakSelf = self;
    [FLFidel present:rootViewController onCardLinkedCallback:^(FLLinkResult * _Nonnull result) {
        NSDictionary *adaptedResult = [weakSelf.objectAdapter dictionaryFrom:result];
        callback(@[[NSNull null], adaptedResult]);
    } onCardLinkFailedCallback:^(FLLinkError * _Nonnull error) {
        NSDictionary *adaptedError = [weakSelf.objectAdapter dictionaryFrom:error];
        [weakSelf sendEventWithName:@"CardLinkFailed" body:adaptedError];
    }];
}

-(NSArray<NSString *> *)supportedEvents {
    return @[@"CardLinkFailed"];
}

-(NSDictionary *)constantsToExport {
    NSMutableDictionary *constants = [self.setupAdapter.constantsToExport mutableCopy];
    [constants addEntriesFromDictionary:self.adapter.constantsToExport];
    return [constants copy];
}

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

-(dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

@end
