/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import { StyleSheet, Text, View, Button } from 'react-native';
import Fidel from 'react-native-fidel';

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
    Fidel.setup ({
      apiKey: 'Your API Key',
      programId: 'Your Program ID'
    })
    Fidel.setOptions ({
      // bannerImage: resolvedImage,
      // country: Fidel.Country.canada,
      autoScan: false,
      metaData: {'meta-data-1': 'value1'},
      companyName: 'My RN Company',
      deleteInstructions: 'My custom delete instructions',
      privacyUrl: 'https://fidel.uk',
    });
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Fidel React Native SDK example</Text>
        <Text style={styles.instructions}>To show started, tap the button below.</Text>
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
