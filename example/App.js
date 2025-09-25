/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {Button, StyleSheet, Text, View} from 'react-native';
import Fidel, {
  ENROLLMENT_RESULT,
  ERROR,
} from 'fidel-react-native';

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isShown: true,
    };
    this.configureFidel();
  }

  configureFidel() {
    const myImage = require('./demo_images/fdl_test_banner.png');
    const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');
    const resolvedImage = resolveAssetSource(myImage);

    //this is the default value for supported card schemes,
    //but you can remove the support for some of the card schemes if you want to
    const cardSchemes = [Fidel.CardScheme.visa, Fidel.CardScheme.mastercard];

    const countries = [
      Fidel.Country.unitedKingdom,
      Fidel.Country.unitedStates,
      Fidel.Country.canada,
    ];

    Fidel.setup(
      {
        sdkKey: 'Your SDK Key',
        programId: 'Your program ID',
        programType: Fidel.ProgramType.transactionSelect,
        options: {
          bannerImage: resolvedImage,
          allowedCountries: countries,
          defaultSelectedCountry: Fidel.Country.unitedStates,
          supportedCardSchemes: cardSchemes,
          metaData: {userId: 1234},
        },
        consentText: {
          companyName: '[Your Company Name]',
          termsAndConditionsUrl: 'https://fidel.uk',
          privacyPolicyUrl: 'https://fidel.uk',
          programName: '[Your program name]',
          deleteInstructions: '[going to your account settings]',
        },
      },
      result => {
        switch (result.type) {
          case ENROLLMENT_RESULT:
            console.log('card was enrolled: ' + result.enrollmentResult.cardId);
            break;
          case ERROR:
            this.handleError(result.error);
            break;
        }
      },
    );
  }

  onButtonPress = () => {
    Fidel.start();
  };

  handleError = error => {
    console.log('Error message: ' + error.message);
    switch (error.type) {
      case Fidel.ErrorType.userCanceled:
        console.log('User canceled the process');
        break;
      case Fidel.ErrorType.sdkConfigurationError:
        console.log('Please configure the Fidel SDK correctly');
        break;
      case Fidel.ErrorType.deviceNotSecure:
        console.log(
          "Your card details are considered sensitive information. Make sure you're providing them only using secure devices.",
        );
        break;
      case Fidel.ErrorType.enrollmentError:
        this.handleEnrollmentError(error);
        break;
    }
  };

  handleEnrollmentError = enrollmentError => {
    switch (enrollmentError.subtype) {
      case Fidel.EnrollmentErrorType.cardAlreadyExists:
        console.log('This card was already enrolled.');
        break;
      case Fidel.EnrollmentErrorType.invalidProgramId:
        console.log('Please configure Fidel with a valid program ID.');
        break;
      case Fidel.EnrollmentErrorType.invalidSdkKey:
        console.log('Please configure Fidel with a valid SDK Key.');
        break;
      case Fidel.EnrollmentErrorType.inexistentProgram:
        console.log('Please configure Fidel with a valid program ID.');
        break;
      case Fidel.EnrollmentErrorType.unexpected:
        console.log('Unexpected enrollment error occurred.');
        break;
    }
  };

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Fidel React Native SDK example</Text>
        <Text style={styles.instructions}>
          To get started, tap the button below.
        </Text>
        <View style={{height: 10}} />
        <Button onPress={this.onButtonPress} title="Start" color="#3846ce" />
        <View style={{height: 10}} />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    color: '#333333',
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
