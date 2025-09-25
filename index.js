import { NativeEventEmitter, NativeModules } from "react-native";
import { version } from "./package.json";

const { NativeFidelBridge } = NativeModules;

export default class Fidel {
  static emitter = new NativeEventEmitter(NativeFidelBridge);
  static Country = NativeFidelBridge.Country;
  static CardScheme = NativeFidelBridge.CardScheme;
  static ProgramType = NativeFidelBridge.ProgramType;
  static ErrorType = NativeFidelBridge.ErrorType;
  static EnrollmentErrorType = NativeFidelBridge.EnrollmentErrorType;

  static setup(params, callback) {
    if (this.eventSubscription != null) {
      this.eventSubscription.remove();
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
    Fidel.identifyMetricsDataSource()
    NativeFidelBridge.start();
  }

  static identifyMetricsDataSource() {
    NativeFidelBridge.identifyMetricsDataSource("rn", version);
  }
}

export const ENROLLMENT_RESULT = NativeFidelBridge.ResultType.EnrollmentResult;
export const ERROR = NativeFidelBridge.ResultType.Error;
