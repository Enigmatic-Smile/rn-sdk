
import { NativeEventEmitter, NativeModules } from 'react-native';

const { NativeFidelBridge } = NativeModules;

class Fidel {
    
    static setup(params, callback) { 
        if (this.eventSubscription != null) {
            this.eventSubscription.remove();
        }
        this.eventSubscription = Fidel.emitter.addListener("ResultReceived", result => callback(result));
        NativeFidelBridge.setup(params);
    }
    static setOptions(params) { NativeFidelBridge.setOptions(params); }
    static start() {
        NativeFidelBridge.start();
    }
}

Fidel.emitter = new NativeEventEmitter(NativeFidelBridge);
Fidel.Country = NativeFidelBridge.Country
Fidel.CardScheme = NativeFidelBridge.CardScheme

module.exports = Fidel;