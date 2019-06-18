package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

public final class CallbackSpy implements Callback {

    public Boolean didInvoke = false;
    public ReadableMap receivedResultMap;
    public ReadableMap receivedErrorMap;

    @Override
    public void invoke(Object... args) {
        receivedErrorMap = (ReadableMap)args[0];
        receivedResultMap = (ReadableMap)args[1];
        didInvoke = true;
    }
}
