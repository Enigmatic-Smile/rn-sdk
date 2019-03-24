
#import "Fidel.h"
#import <Fidel/Fidel-Swift.h>
#import "RCTConvert+Country.h"

@implementation Fidel

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(setOptions:(NSDictionary *)options)
{
    NSObject *bannerImageOption = options[@"bannerImage"];
    UIImage *bannerImage = [RCTConvert UIImage:bannerImageOption];
    [FLFidel setBannerImage:bannerImage];
    
    FLCountry country = [RCTConvert FLCountry:options[@"country"]];
    FLFidel.country = country;
}

RCT_EXPORT_METHOD(openForm)
{
    [FLFidel setApiKey:@"your API key"];
    [FLFidel setProgramId:@"your program id"];
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    [FLFidel present:rootViewController onCardLinkedCallback:nil onCardLinkFailedCallback:nil];
}

-(NSDictionary *)constantsToExport {
    return @{@"Country": FLCountryValues};
}

- (BOOL)requiresMainQueueSetup {
    return YES;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

@end
  
