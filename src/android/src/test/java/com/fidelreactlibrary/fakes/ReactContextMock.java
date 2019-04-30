package com.fidelreactlibrary.fakes;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;

public final class ReactContextMock extends ReactApplicationContext {
    public ReactContextMock(Context context) {
        super(context);
    }
}
