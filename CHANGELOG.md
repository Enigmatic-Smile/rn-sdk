# Fidel React Native bridge library change log

## 1.3.1

- Fixes an Android issue in the latest React Native versions, when specifying the banner image in release environment.
- Checking for `null` values, in mandatory SDK fields, to prevent unclear bugs.

## 1.3.0

- Localised the SDK for French and Swedish users.
- The terms & conditions text now adjusts to the card scheme name that the user inputs (Visa, MasterCard or Amex).
- If you set the default country of the SDK to USA or Canada (with `country` option) or, if you do not set a default country, the terms and conditions text will adjust depending on the country you have set. For USA & Canada, the following would be an example Terms & Conditions text, for Cashback Inc (an example company):

*By submitting your card information and checking this box, you authorize Visa to monitor and share transaction data with Fidel (our service provider) to participate in  program. You also acknowledge and agree that Fidel may share certain details of your qualifying transactions with Cashback Inc to enable your participation in  program and for other purposes in accordance with the Cashback Inc Terms and Conditions, Cashback Inc privacy policy and Fidel’s Privacy Policy. You may opt-out of transaction monitoring on the linked card at any time by contacting support.*

For the rest of the world:

*I authorise Visa to monitor my payment card to identify transactions that qualify for a reward and for Visa to share such information with Cashback Inc, to enable my card linked offers and target offers that may be of interest to me. For information about Cashback Inc privacy practices, please see the privacy policy. You may opt-out of transaction monitoring on the payment card you entered at any time by contacting support.*

- Added the `programName` and `termsConditionsUrl` options. They are used when building the new USA / Canada specific terms and conditions text that the user must agree with, before linking a card. If you set the `country` option to a USA or Canada, it's mandatory for you to provide your terms and conditions URL via `termsConditionsUrl`. If you do not provide it, you will receive an error when you try to open Fidel's linking screen.
- Fixed bug that returned an empty link result in Android, when linking is successful.
