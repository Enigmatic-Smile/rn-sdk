
import { NativeEventEmitter, NativeModules } from 'react-native';

const { NativeFidelBridge } = NativeModules;

class Fidel {
    
    static setup(params) { NativeFidelBridge.setup(params) }
    static setOptions(params) { NativeFidelBridge.setOptions(params) }
    static openForm(callback) {
        if (this.eventSubscription != null) {
            this.eventSubscription.remove();
        }
        this.eventSubscription = Fidel.emitter.addListener(
            "CardLinkFailed",
            error => callback(error, null)
        );
        NativeFidelBridge.openForm(callback);
    }
}

Fidel.emitter = new NativeEventEmitter(NativeFidelBridge);
Fidel.Country = NativeFidelBridge.Country
Fidel.CardScheme = NativeFidelBridge.CardScheme

module.exports = Fidel;