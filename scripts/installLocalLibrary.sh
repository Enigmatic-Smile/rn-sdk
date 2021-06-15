#!/bin/bash

# Get the version of the library
version=`ruby -rjson -e 'j = JSON.parse(File.read("package.json")); puts j["version"]'`

# Make a TGZ package of the library
npm pack

# Get the TGZ package file name
localLibraryPackage=fidel-react-native-$version.tgz

# Switch to the example project, in order to install the library in the example project
cd example

# Install the local library code in the example project, from the TGZ package
yarn add file:../$localLibraryPackage

# Switch to the iOS project folder
cd ios

# Install the pods, to be sure that the latest code is integrated
pod install