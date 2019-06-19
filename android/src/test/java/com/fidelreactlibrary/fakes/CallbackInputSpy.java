package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.Callback;
import com.fidelreactlibrary.events.CallbackInput;

public final class CallbackInputSpy implements CallbackInput {
    public Callback receivedCallback;
    @Override
    public void callbackIsReady(Callback callback) {
        receivedCallback = callback;
    }
}
