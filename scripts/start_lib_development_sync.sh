#!/bin/bash
echo 'Watching the `android`, `ios` folders and `index.js` and `fidel-react-native.podspec` files from the `example/node_modules/fidel-react-native` folder and copies changes into the root folder of the RN SDK repo.\nPress Ctrl+C to stop watching for changes.'

while true; do
    rsync --archive --update --delete --exclude './example/node_modules/fidel-react-native/android/build' ./example/node_modules/fidel-react-native/android .
    rsync --archive --update --delete ./example/node_modules/fidel-react-native/ios .
    rsync --archive --update --delete ./example/node_modules/fidel-react-native/index.js .
    rsync --archive --update --delete ./example/node_modules/fidel-react-native/fidel-react-native.podspec .
    sleep 2
done
