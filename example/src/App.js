/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import { StyleSheet, Text, View, Button } from 'react-native';
import Fidel from 'fidel-react-native';

type Props = {};
export default class App extends Component<Props> {
  onLinkButtonPress() {
    Fidel.openForm((error, result) => {
      if (error) {
        console.debug(error);
      } else {
        console.info(result);
      }
    });
  }
  render() {
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

    Fidel.setup ({
      apiKey: 'Your API Key',
      programId: 'Your Program ID'
    })
    Fidel.setOptions ({
      country: Fidel.Country.canada,
      supportedCardSchemes: Array.from(cardSchemes),
      autoScan: false,
      metaData: {'meta-data-1': 'value1'},
      companyName: 'My RN Company',
      deleteInstructions: 'My custom delete instructions!',
      privacyUrl: 'https://fidel.uk',
      termsConditionsUrl: 'https://fidel.uk',
      programName: 'My program name',
    });
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Fidel React Native SDK example</Text>
        <Text style={styles.instructions}>To get started, tap the button below.</Text>
        <Button
          onPress={this.onLinkButtonPress}
          title="Link a card"
          color="#3846ce"
        />
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
