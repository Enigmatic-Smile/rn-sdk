
#import "Fidel.h"
#import <React/RCTLog.h>

@implementation Fidel

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(showMessage:(NSString *)message)
{
  RCTLogInfo(@"Print message: %@", message);
}

@end
  