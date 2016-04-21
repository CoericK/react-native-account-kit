//
//  RNAccountKit.m
//  RNAccountKit
//
//  Created by Erick on 4/21/16.
//  Copyright © 2016 erickarroyo. All rights reserved.
//

#import "RNAccountKit.h"
#import "RCTLog.h"
#import <AccountKit/AccountKit.h>

@implementation RNAccountKit

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
    RCTLogInfo(@"Pretending to create an event %@ at %@", name, location);
}

@end
