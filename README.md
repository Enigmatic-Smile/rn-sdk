# Fidel React Native SDK

This SDK is a bridge between React Native and Fidel's native iOS and Android SDKs. It helps you to add card linking technology to your React Native apps in minutes. It captures credit/debit card numbers securely and links them to your programs.

![Demo GIF](https://cl.ly/a47b1852f029/Screen%252520Recording%2525202018-09-18%252520at%25252004.34%252520PM.gif)

## Documentation

Our website includes all the [React Native SDK documentation](https://docs.fidelapi.com/docs/select/sdks/react-native/guide-v2).

## How to run the example application

1. Follow the React Native [environment setup instructions](https://reactnative.dev/docs/environment-setup). The example app is not an Expo app, so please follow the "React Native CLI Quickstart" guide.
2. Open the terminal in the "example" folder.
3. Install React Native dependencies (which include the Fidel react native library) by running `yarn` in the terminal
4. To run the iOS example app:
   1. Switch to the "example/ios" folder in your terminal.
   2. Run `pod install` to install the Cocoapods project dependencies
   3. Open the `example.xcworkspace` in Xcode
   4. Run the project (eventually, it should open another terminal windown that runs Metro)
5. To run the Android example app:
   1. Open the "example/android" project in Android Studio.
   2. Open a terminal window in the "example" folder.
   3. Run the following command to start Metro: `npx react-native start`
   4. Run the project on your preferred emulator/device.

## Feedback

The Fidel API SDKs is in active development, so we welcome your feedback!

Get in touch:

GitHub Issues - For SDK issues and feedback
