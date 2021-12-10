package com.fidelreactlibrary.events;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class ResultAvailableEventEmitter implements DataProcessor<WritableMap> {

    private final ReactApplicationContext reactContext;

    public ResultAvailableEventEmitter(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    @Override
    public void process(WritableMap data) {
        reactContext
                .getJSModule(RCTDeviceEventEmitter.class)
                .emit("ResultAvailable", data);
    }
}
