/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View} from 'react-native';
import Fidel from 'react-native-fidel';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {
  render() {
    const myImage = require('./src/images/fdl_test_banner.png');
    const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');
    const resolvedImage = resolveAssetSource(myImage);
    Fidel.setup ({
      apiKey: 'pk_test_4bb15564-01da-4f7b-a108-2de19e96e136',
      programId: 'bd0e2eac-b648-47cd-814f-1e52c08e2b03'
    })
    Fidel.setOptions ({
      bannerImage: resolvedImage,
      // country: Fidel.Country.canada,
      autoScan: false,
      metaData: {'meta-data-1': 'value1'},
      companyName: 'My RN Company',
      deleteInstructions: 'My custom delete instructions',
      privacyUrl: 'https://fidel.uk',
    });
    Fidel.openForm((error, result) => {
      if (error) {
        console.debug(error);
      } else {
        console.info(result);
      }
    });
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
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
