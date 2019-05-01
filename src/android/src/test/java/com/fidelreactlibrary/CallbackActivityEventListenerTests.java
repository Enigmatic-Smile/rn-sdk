package com.fidelreactlibrary;

import com.facebook.react.bridge.Callback;
import com.fidelreactlibrary.events.CallbackActivityEventListener;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CallbackActivityEventListenerTests {

    private boolean didInvoke = false;

    @Test
    public void test_WhenRequestCodeIsNotFidelCode_DontCallCallback() {
        CallbackActivityEventListener sut = new CallbackActivityEventListener();
        Callback callbackSpy = new Callback() {
            @Override
            public void invoke(Object... args) {
                didInvoke = true;
            }
        };
        sut.callbackIsReady(callbackSpy);
        sut.onActivityResult(null,0,0,null);
        assertFalse(didInvoke);
    }
}
