
import { NativeEventEmitter, NativeModules } from 'react-native';

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
        if (callback != null && callback != undefined) {
            this.eventSubscription = Fidel.emitter.addListener("ResultAvailable", result => callback(result));
        }
        NativeFidelBridge.setup(params);
    }
    static start() {
        NativeFidelBridge.start();
    }
}

export const ENROLLMENT_RESULT = NativeFidelBridge.ResultType.EnrollmentResult;
export const ERROR = NativeFidelBridge.ResultType.Error;
export const VERIFICATION_RESULT = NativeFidelBridge.ResultType.VerificationResult;