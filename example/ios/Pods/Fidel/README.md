# FIDEL iOS SDK

Our SDK helps you add card linking technology to your iOS apps in minutes. It captures credit/debit card numbers securely and links them to your programs.

![Demo GIF](https://cl.ly/84d481392da6/Screen%252520Recording%2525202018-09-18%252520at%25252004.49%252520PM.gif)

### Installation

We recommend using [CocoaPods][642d6fa5] to integrate Fidel SDK with your project.

[642d6fa5]: https://cocoapods.org/ "CocoaPods"

Add a `Podfile` (if you don't have one already), by running the following command: `pod init`.

##### Step 1
Add Fidel pod (for Swift 4.2.1):

```ruby
pod 'Fidel'
```

or if you're using Swift 4.1:

```ruby
pod 'Fidel', '~>1.3.6'
```

or if you're using Swift 4.0.2:

```ruby
pod 'Fidel', '~>1.2.7'
```

or in case you're on **Swift 3.0**, use the `1.1.7` tag instead:

```ruby
pod 'Fidel', '~>1.1.7'
```

##### Step 2
In order to allow scanning cards with the camera, make sure to add the key `NSCameraUsageDescription` to your app's `Info.plist` and set the value to a string describing why your app needs to use the camera (e.g. "To scan credit cards."). This string will be displayed when the app initially requests permission to access the camera.

##### Step 3 (skip if you have a Swift project)
If you have an Objective-C project and did not add any Swift code yet, please set the `Always Embed Swift Standard Libraries` flag in Build Settings to `YES`. For more detailed information about this setting, please read this [Apple material](https://developer.apple.com/library/archive/qa/qa1881/_index.html).

##### Troubleshooting
In case Cocoapods doesn't find the Fidel specs or it finds older specs, try updating with `pod update`. After updating, run `pod install`.

### Usage

Import the SDK in your code:
##### Swift
```swift
import Fidel
```

##### Objective-C
```objectivec
#import <Fidel/Fidel-Swift.h>
```

Set your public SDK Key (`pk_test` or `pk_live`) and the `programId` you want to link cards to:

##### Swift
```swift
Fidel.apiKey = "pk_test_7ty6i7..."
Fidel.programId = "3a7a169a-..."
```

##### Objective-C
```objectivec
[FLFidel setApiKey:@"pk_test_7ty6i7..."];
[FLFidel setProgramId:@"3a7a169a-..."];
```

To start card scanning automatically:

##### Swift
```swift
Fidel.autoScan = true
```

##### Objective-C
```objectivec
[FLFidel setAutoScan:YES];
```

To customise the view with a custom banner:

##### Swift
```swift
Fidel.bannerImage = UIImage(named: "some_image_asset.png")
```

##### Objective-C
```objectivec
[FLFidel setBannerImage:[UIImage imageNamed:@"myImage"]];
```

If you don't set a banner image, we'll just remove the top banner space entirely and card linking UI will stay on top.

Then, present the Fidel view controller:

##### Swift
```swift
Fidel.present(presentingViewControllerInstance)
```

##### Objective-C
```objectivec
[FLFidel present:self onCardLinkedCallback:nil onCardLinkFailedCallback:nil];
```

Optionally, you can pass callbacks to be notified if the card was linked:

##### Swift
```swift
Fidel.present(self, onCardLinkedCallback: { (card: LinkResult) in
	print(card.id)
}, onCardLinkFailedCallback: { (err: LinkError) in
	print(err.message)
})
```

##### Objective-C
```objectivec
[FLFidel present:self onCardLinkedCallback:^(FLLinkResult * _Nonnull result) {
    NSLog(@"%@", result);
} onCardLinkFailedCallback:^(FLLinkError * _Nonnull error) {
    NSLog(@"%@", error);
}];
```

#### Customize checkbox consent text
For customizing the checkbox consent, please use the following APIs:

```swift
Fidel.companyName = "Your Company Name Inc." //(Maximum 60 characters);
Fidel.privacyURL = "https://yourcompany.com/privacyURL" //(must be a valid URL)
Fidel.deleteInstructions = "Your delete instructions" //(Maximum 60 characters);
```

The default for `companyName` is `"Company Name"`.
The default for `deleteInstructions` is `"going to your account settings"`.

#### Default country

Set a default country the SDK should use with 
```swift
Fidel.country = .unitedKingdom
```
When you set a default country, the card linking screen will not show the country picker UI. The other options, for now, are: `.unitedStates`, `.ireland`, `.sweden`, `.japan`, `.noDefault`.



### Documentation

In test environment use our VISA, Mastercard or American Express test card numbers:

VISA: _4444000000004***_ (the last 3 numbers can be anything)

Mastercard: _5555000000005***_ (the last 3 numbers can be anything)

American Express: _3400000000003**_ or _3700000000003**_ (the last 2 numbers can be anything)

#### Detect when user canceled card linking

Check the `code` property of the error object you receive in the `onCardLinkFailedCallback` closure. When the user cancels card linking, the `code` is `user-canceled`. The error message is `User canceled card linking`.

### Feedback

The Fidel iOS SDK is in active development, we welcome your feedback!

Get in touch:
* GitHub Issues - For SDK issues
* [Developers Slack Channel](https://fidel-developers-slack-invites.herokuapp.com) for personal support and feedback at any phase of integration


### License

The FIDEL iOS SDK is open source and available under the MIT license. See the LICENSE file for more info.
