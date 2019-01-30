
#import "Fidel.h"
#import <Fidel/Fidel-Swift.h>

@implementation Fidel

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openForm)
{
    [FLFidel setApiKey:@"your API key"];
    [FLFidel setProgramId:@"your program id"];
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    [FLFidel present:rootViewController onCardLinkedCallback:nil onCardLinkFailedCallback:nil];
}

@end
  
