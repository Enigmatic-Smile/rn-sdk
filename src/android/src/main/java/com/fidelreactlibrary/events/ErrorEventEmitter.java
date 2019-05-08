package com.fidelreactlibrary.events;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class ErrorEventEmitter implements DataProcessor<WritableMap> {

    private RCTDeviceEventEmitter deviceEventEmitter;

    public ErrorEventEmitter(ReactApplicationContext reactContext) {
        deviceEventEmitter = reactContext.getJSModule(RCTDeviceEventEmitter.class);
    }

    @Override
    public void process(WritableMap data) {
        deviceEventEmitter.emit("ErrorName", data);
    }
}
