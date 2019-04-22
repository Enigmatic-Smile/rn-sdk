
# react-native-fidel

## Getting started

`$ npm install react-native-fidel --save`

### Mostly automatic installation

`$ react-native link react-native-fidel`

### Manual installation

#### iOS

##### Step 1: Add the Fidel React Native iOS project as a dependency

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-fidel` and add `Fidel.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libFidel.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Under your target's `Build Settings`, make sure to set `YES` for `Always Embed Swift Standard Libraries`. That's because, by default, your project might not need it. It needs to be `YES` because otherwise the project will not find the Swift libraries to be able to run our native iOS SDK.

##### Step 2: Add the Native iOS SDK as a dependency

You can use Cocoapods or install the library as a dynamic library.

##### Method 1: Using Cocoapods

5. Add a `Podfile` in your `ios/` folder of your React Native project. It should include the following dependency:

```ruby
pod 'Fidel'
```

Check our [example Podfile](https://github.com/FidelLimited/rn-sdk/blob/master/example/ios/Podfile) to see the simplest `Podfile` you need to link with our native iOS SDK.
If you use an older Swift version, please check our [iOS SDK README](https://github.com/FidelLimited/fidel-ios#readme). You'll find a suitable version you should set for your Cocoapods dependency.

6. In your `Podfile`, make sure to set the iOS platform 9.1 or above:

```ruby
# Use iOS 9 and above
platform :ios, '9.1'
```

7. Run `pod install` in your terminal.
8. Make sure to use the new `.xcworkspace` created by Cocoapods when you run your iOS app. React Native should use it by default.

#### Android

1. Append the following lines to `android/settings.gradle`:

```java
include ':react-native-fidel'
project(':react-native-fidel').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-fidel/android')
```

2. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

```java
implementation project(':react-native-fidel')
```

3. Append Jitpack to `android/build.gradle`:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```

4. Make sure that the `minSdkVersion` is the same or higher than the `minSdkVersion` of our native Android SDK:

```java
buildscript {
  ext {
    ...
    minSdkVersion = 16
    ...
  }
}
```

5. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import com.reactlibrary.FidelPackage;` to the imports at the top of the file
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

6. Ensure that you have *Google Play Services* installed.

For a physical device you need to search on Google for *Google Play Services*. There will be a link that takes you to the Play Store and from there you will see a button to update it (*do not* search within the Play Store).

## Usage

Import Fidel in your RN project:

```javascript
import Fidel from 'react-native-fidel';
```

Set the API key and your program ID:

```javascript
Fidel.setup({
  apiKey:'your api key',
  programId: 'your program id'
})
```

Set the options that create the experience of linking a card with Fidel

```javascript
Fidel.setOptions({
  autoScan: true,
  bannerImage: 
})
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