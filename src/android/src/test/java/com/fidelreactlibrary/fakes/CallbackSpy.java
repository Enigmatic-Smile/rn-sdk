package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.Callback;

public final class CallbackSpy implements Callback {

    public Boolean didInvoke = false;

    @Override
    public void invoke(Object... args) {
        didInvoke = true;
    }
}
