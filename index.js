
import { NativeEventEmitter, NativeModules } from 'react-native';

const { NativeFidelBridge } = NativeModules;

class Fidel {
    
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

Fidel.emitter = new NativeEventEmitter(NativeFidelBridge);
Fidel.Country = NativeFidelBridge.Country;
Fidel.CardScheme = NativeFidelBridge.CardScheme;
Fidel.ProgramType = NativeFidelBridge.ProgramType;

module.exports = Fidel;