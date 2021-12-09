
#import "FidelNativeModule.h"
#import "fidel_react_native-Swift.h"
#import "FLRNImageFromRNAdapter.h"

@interface FidelNativeModule()

@property (nonatomic, strong) FLRNSetupAdapter *setupAdapter;
@property (nonatomic, strong) FLRNExportedConstantsProvider *exportedConstantsProvider;
@property (nonatomic, strong) FLRNFlowStarter *flowStarter;
@property (nonatomic, strong) FLRNResultsObserver *resultsObserver;

@end

@implementation FidelNativeModule

RCT_EXPORT_MODULE(NativeFidelBridge)

-(instancetype)init {
    self = [super init];
    if (self) {
        id<FLRNImageAdapter> imageAdapter = [[FLRNImageFromRNAdapter alloc] init];
        _setupAdapter = [[FLRNSetupAdapter alloc] initWithImageAdapter: imageAdapter];
        _exportedConstantsProvider = [[FLRNExportedConstantsProvider alloc] init];
        _flowStarter = [[FLRNFlowStarter alloc] init];
        _resultsObserver = [[FLRNResultsObserver alloc] init];
    }
    return self;
}

RCT_EXPORT_METHOD(setup:(NSDictionary *)setupInfo) {
    [self.setupAdapter setupWith:setupInfo];
}

RCT_EXPORT_METHOD(start) {
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    [self.flowStarter startFrom: rootViewController];
}

- (void)addListener:(NSString *)eventName {
    [super addListener:eventName];
    __weak typeof(self) weakSelf = self;
    [self.resultsObserver startObservingWith:^(NSDictionary * _Nonnull result) {
        [weakSelf sendEventWithName:@"ResultAvailable" body:result];
    }];
}

- (void)removeListeners:(double)count {
    [super removeListeners:count];
    [self.resultsObserver stopObserving];
}

-(NSArray<NSString *> *)supportedEvents {
    return @[@"ResultAvailable"];
}

-(NSDictionary *)constantsToExport {
    return self.exportedConstantsProvider.constants;
}

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

-(dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

@end
