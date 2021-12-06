/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  Button,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import Fidel from 'fidel-react-native';

const App = () => {

  const onButtonPress = () => {
    const myImage = require('./demo_images/fdl_test_banner.png');
    const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');
    const resolvedImage = resolveAssetSource(myImage);

    //this is the default value for supported card schemes,
    //but you can remove the support for some of the card schemes if you want to
    const cardSchemes = [
      Fidel.CardScheme.visa,
      Fidel.CardScheme.mastercard,
      Fidel.CardScheme.americanExpress,
    ];

    const countries = [
      Fidel.Country.unitedArabEmirates,
      Fidel.Country.unitedKingdom,
      Fidel.Country.unitedStates,
      Fidel.Country.japan,
      Fidel.Country.sweden,
      Fidel.Country.ireland,
      Fidel.Country.canada,
    ];

    Fidel.setup ({
      sdkKey: 'Your API Key',
      programId: 'Your Program ID',
      companyName: 'Your Company Name',
      options: {
        bannerImage: resolvedImage,
        allowedCountries: countries,
        supportedCardSchemes: cardSchemes,
        shouldAutoScanCard: false,
      },
      consentText: {
        termsAndConditionsUrl: 'https://fidel.uk',
        privacyPolicyUrl: 'https://fidel.uk',
        programName: 'Your program name',
        deleteInstructions: "following our delete instructions",
      },
    });


    // Fidel.setup ({
    //   sdkKey: 'Your API Key',
    //   programId: 'Your Program ID',
    //   programType: ProgramType.transactionStream,
    //   companyName: 'My RN Company',
    //   terms: {
    //     termsAndConditionsUrl: 'https://fidel.uk',
    //     deleteInstructions: 'My custom delete instructions!',
    //     privacyPolicyUrl: 'https://fidel.uk',
    //     programName: 'My program name',
    //   },
    //   options: {
    //     bannerImage: resolvedImage,
    //     allowedCountries: countries,
    //     supportedCardSchemes: cardSchemes,
    //     shouldAutoScanCard: false,
    //     metaData: {'meta-data-1': 'value1'},
    //   }
    // }, callback: (result) => {
      // const exampleResult = {
      //   enrollmentResult: {
      //     cardId: "asd"
      //   },
      //   error: {
      //     type: 
      //   },
      //   verificationResult: {
      //     sucessful: false
      //   }
      // }

      // if (exampleObject[FidelResult.Enrollment]) {

      // }

      // {
      //   type: "error" | "enrollmentResult" | "verificationResult"
      //   enrollmentResult: EnrollmentResult,
      //   error: FidelError,
      //   verificationResult: VerificationResult
      // }

    //   switch (result.type) {
    //     case ENROLLMENT_RESULT:
    //       console.log("card was enrolled: " + result.enrollmentResult.cardId);
    //       break;
    //     case FidelResult.Error:
    //       console.log("encountered error: " + result.error.message);
    //       break;
    //     case FidelResult.VerificationSuccessful:
    //       console.log("card verification was successful 🎉");
    //       break;
    //   }
    // });

    Fidel.start();
  }

  // handleError = (error) => {
  //   if (result.enrollmentError != null) {
  //     console.log("");
  //   }
  //   switch (error.type) {
  //     case FidelErrorType.UserCanceled:
  //       console.log("user canceled the process");
  //       break;
  //     case FidelErrorType.SdkConfigurationError:
  //       console.log("Please configure the Fidel SDK correctly");
  //       break;
  //     case FidelErrorType.EnrollmentError:
  //       console.log("An enrollment error ocurred");
  //     case FidelErrorType.VerificationError:
  //       break;
  //   }
  // }

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>Fidel React Native SDK example</Text>
      <Text style={styles.instructions}>To get started, tap the button below.</Text>
      <Button
        onPress={onButtonPress}
        title="Link a card"
        color="#3846ce"
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});


export default App;
