
# Fidel React Native bridge SDK

This SDK is a bridge between React Native and Fidel's native iOS and Android SDKs. It helps you to add card linking technology to your React Native apps in minutes. It captures credit/debit card numbers securely and links them to your programs.

![Demo GIF](https://cl.ly/a47b1852f029/Screen%252520Recording%2525202018-09-18%252520at%25252004.34%252520PM.gif)

## Getting started

`$ npm install fidel-react-native --save`

## Manual installation

### iOS

**Note:** `fidel-react-native` does not support `react-native 0.60`, so please make sure you bootstrap your projects using `react-native init --version="react-native@0.59.0" yourProject`.

#### Step 1: Add the Fidel React Native iOS project as a dependency

**1.** In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`

**2.** Go to `node_modules` ➜ `fidel-react-native` and add `Fidel.xcodeproj`

**3.** In XCode, in the project navigator, select your project. Add `libFidel.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`

**4.** Under your target's `Build Settings`, make sure to set `YES` for `Always Embed Swift Standard Libraries`. That's because, by default, your project might not need it. It needs to be `YES` because otherwise the project will not find the Swift libraries to be able to run our native iOS SDK.

#### Step 2: Add the Native iOS SDK as a dependency

You can use Cocoapods or install the library as a dynamic library.

**5.** Add a `Podfile` in your `ios/` folder of your React Native project. It should include the following dependency:

```ruby
use_frameworks!
pod 'Fidel'
```

Here is the simplest `Podfile` you need to link with our native iOS SDK:

```ruby
platform :ios, '9.1'

workspace 'example'

target 'example' do
    use_frameworks!
    pod 'Fidel'
end
```

**6.** If you use an older Swift version, you should install a different `Fidel` pod version. If your project uses Swift `4.2.1`, for example, your Podfile should include `pod Fidel, '~>1.4'`, *not* `pod 'Fidel'` (which installs the latest version of the latest Swift supported version). Please check our [iOS SDK README (step 1)](https://github.com/FidelLimited/fidel-ios#step-1). You'll find a suitable version you should set for our Fidel iOS SDK.

**7.** Run `pod install` in your terminal.

**8.** Make sure to use the new `.xcworkspace` created by Cocoapods when you run your iOS app. React Native should use it by default.

**9.** In order to allow scanning cards with the camera, make sure to add the key `NSCameraUsageDescription` to your iOS app `Info.plist` and set the value to a string describing why your app needs to use the camera (e.g. "To scan credit cards."). This string will be displayed when the app initially requests permission to access the camera.

### Android

**1.** Append the following lines to `android/settings.gradle`:

```java
include ':fidel-react-native'
project(':fidel-react-native').projectDir = new File(rootProject.projectDir, '../node_modules/fidel-react-native/android')
```

**2.** Insert the following lines inside the dependencies block in `android/app/build.gradle`:

```java
implementation project(':fidel-react-native')
```

**3.** Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.fidelreactlibrary.FidelPackage;` to the imports at the top of the file
- Add `new FidelPackage()` to the list returned by the `getPackages()` method:

```java
protected List <ReactPackage> getPackages() {
  return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new FidelPackage(),
          //you might have other Packages here as well.
      );
}
```

**4.** Append Jitpack to `android/build.gradle`:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```

**5.** Make sure that the `minSdkVersion` is the same or higher than the `minSdkVersion` of our native Android SDK:

```java
buildscript {
  ext {
    ...
    minSdkVersion = 19
    ...
  }
}
```

**6.** Ensure that you have *Google Play Services* installed.

For a physical device you need to search on Google for *Google Play Services*. There will be a link that takes you to the Play Store and from there you will see a button to update it (*do not* search within the Play Store).

## How to use Fidel's React Native SDK

Import Fidel in your RN project:

```javascript
import Fidel from 'fidel-react-native';
```

Set up Fidel with your API key and your program ID. Without them you can't open the Fidel card linking UI:

```javascript
Fidel.setup({
  apiKey:'your api key',
  programId: 'your program id'
})
```

Set the options that create the experience of linking a card with Fidel:

```javascript
const myImage = require('./images/fdl_test_banner.png');
const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');
const resolvedImage = resolveAssetSource(myImage);

//this is the default value for supported card schemes,
//but you can remove the support for some of the card schemes if you want to
const cardSchemes = new Set([
  Fidel.CardScheme.visa,
  Fidel.CardScheme.mastercard,
  Fidel.CardScheme.americanExpress
]);

Fidel.setOptions({
  bannerImage: resolvedImage,
  country: Fidel.Country.sweden,
  supportedCardSchemes: Array.from(cardSchemes),
  autoScan: false,
  metaData: {'meta-data-1': 'value1'}, // additional data to pass with the card
  companyName: 'My RN Company', // the company name displayed in the checkbox text
  deleteInstructions: 'Your custom delete instructions!',
  privacyUrl: 'https://fidel.uk',
});
```

Open the card linking view by calling:

```javascript
Fidel.openForm((error, result) => {
  if (error) {
    console.error(error);
  } else {
    console.info(result);
  }
});
```

Both `result` and `error` are objects that look like in the following examples:

### Result

```javascript
{
  accountId: "the-account-id"
  countryCode: "GBR" // the country selected by the user, in the Fidel SDK form
  created: "2019-04-22T05:26:45.611Z"
  expDate: "2023-12-31T23:59:59.999Z" // the card expiration date
  expMonth: 12 // for your convenience, this is the card expiration month
  expYear: 2023 // for your convenience, this is the card expiration year
  id: "card-id" // the card ID as registered on the Fidel platform
  lastNumbers: "4001" //last numbers of the card
  live: false
  mapped: false
  metaData: {meta-data-1: "value1"} //the meta data that you specified for the Fidel SDK
  programId: "your program ID, as specified for the Fidel SDK"
  scheme: "visa"
  type: "visa"
  updated: "2019-04-22T05:26:45.611Z"
}
```

### Error

```javascript
{
  code: "item-save" // the code of the error
  date: "2019-04-22T05:34:04.621Z" // the date of the card link request
  message: "Item already exists" // the message of the error
}
```

## Options documentation

### bannerImage

Use this option to customize the topmost banner image with the Fidel UI. Your custom asset needs to be resolved in order to be passed to our native module:

```javascript
const myImage = require('./images/fdl_test_banner.png');
const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');
const resolvedImage = resolveAssetSource(myImage);
Fidel.setOptions({
  bannerImage: resolvedImage
}
```

### country

Set a default country the SDK should use with:

```javascript
Fidel.setOptions({
  country: Fidel.Country.unitedKingdom
});
```

When you set a default country, the card linking screen will not show the country picker UI. The other options, for now, are: `.unitedStates`, `.ireland`, `.sweden`, `.japan`, `.canada`.

### supportedCardSchemes

We currently support _Visa_, _Mastercard_ and _AmericanExpress_, but you can choose to support only one, two or all three. By default the SDK is configured to support all three.

If you set this option to an empty array or to `null`, of course, you will not be able to open the Fidel UI. You must support at least one our supported card schemes.

Please check the example below:

```javascript
//this is the default value for supported card schemes,
//but you can remove the support for some of the card schemes if you want to
const cardSchemes = new Set([
  Fidel.CardScheme.visa,
  Fidel.CardScheme.mastercard,
  Fidel.CardScheme.americanExpress
]);
Fidel.setOptions({
  supportedCardSchemes: Array.from(cardSchemes)
});
```

### autoScan

Set this property to `true`, if you want to open the card scanning UI immediately after executing `Fidel.openForm`. The default value is `false`.

```javascript
Fidel.setOptions({
  autoScan: true
});
```

### metaData

Use this option to pass any other data with the card data:

```javascript
Fidel.setOptions({
  metaData: {'meta-data-key-1': 'value1'}
});
```

### companyName

Set your company name as it will appear in our consent checkbox text. Please set it to a maximum of 60 characters.

```javascript
Fidel.setOptions({
  companyName: 'Your Company Name'
});
```

### deleteInstructions

Write your custom opt-out instructions for your users. They will be displayed in the consent checkbox text as well.

```javascript
Fidel.setOptions({
  deleteInstructions: 'Your custom card delete instructions!'
});
```

### privacyUrl

This is the privacy policy URL that you can set for the consent checkbox text.

```javascript
Fidel.setOptions({
  privacyUrl: 'https://fidel.uk',
});
```

### Documentation

In the test environment please use our VISA, Mastercard or American Express test card numbers. You must use a test API Key for them to work.

VISA: _4444000000004***_ (the last 3 numbers can be anything)

Mastercard: _5555000000005***_ (the last 3 numbers can be anything)

American Express: _3400000000003**_ or _3700000000003**_ (the last 2 numbers can be anything)

## Feedback

The Fidel SDK is in active development, we welcome your feedback!

Get in touch:

GitHub Issues - For SDK issues and feedback
Fidel Developers Slack Channel - [https://fidel.uk/join-us-on-slack](https://fidel.uk/join-us-on-slack) - for personal support at any phase of integration
