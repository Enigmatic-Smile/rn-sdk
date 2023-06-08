import { NativeEventEmitter, NativeModules } from "react-native";

const { NativeFidelBridge } = NativeModules;

export default class Fidel {
  static emitter = new NativeEventEmitter(NativeFidelBridge);
  static Country = NativeFidelBridge.Country;
  static CardScheme = NativeFidelBridge.CardScheme;
  static ProgramType = NativeFidelBridge.ProgramType;
  static ErrorType = NativeFidelBridge.ErrorType;
  static EnrollmentErrorType = NativeFidelBridge.EnrollmentErrorType;
  static VerificationErrorType = NativeFidelBridge.VerificationErrorType;

  static setup(params, callback) {
    if (this.eventSubscription != null) {
      this.eventSubscription.remove();
    }
    const { onCardVerificationStarted } = params;
    if (
      onCardVerificationStarted != null &&
      onCardVerificationStarted != undefined &&
      typeof onCardVerificationStarted === "function"
    ) {
      if (this.onCardVerificationStartedEventSubscription != null) {
        this.onCardVerificationStartedEventSubscription.remove();
      }
      this.onCardVerificationStartedEventSubscription =
        Fidel.emitter.addListener(
          "CardVerificationStarted",
          (consentDetails) => {
            onCardVerificationStarted(consentDetails);
          }
        );
    }
    const { onCardVerificationChoiceSelected } = params;
    if (
      onCardVerificationChoiceSelected != null &&
      onCardVerificationChoiceSelected != undefined &&
      typeof onCardVerificationChoiceSelected === "function"
    ) {
      if (this.onCardVerificationChoiceSelected != null) {
        this.onCardVerificationChoiceSelected.remove();
      }
      this.onCardVerificationChoiceSelected = Fidel.emitter.addListener(
        "CardVerificationChoiceSelected",
        (verificationChoice) => {
          onCardVerificationChoiceSelected(verificationChoice);
        }
      );
    }
    if (
      callback != null &&
      callback != undefined &&
      typeof callback === "function"
    ) {
      this.eventSubscription = Fidel.emitter.addListener(
        "ResultAvailable",
        (result) => callback(result)
      );
    }
    NativeFidelBridge.setup(params);
  }

  static start() {
    NativeFidelBridge.start();
  }

  static verifyCard(params) {
    NativeFidelBridge.verifyCard(params);
  }
}

export const ENROLLMENT_RESULT = NativeFidelBridge.ResultType.EnrollmentResult;
export const ERROR = NativeFidelBridge.ResultType.Error;
export const VERIFICATION_RESULT =
  NativeFidelBridge.ResultType.VerificationResult;
