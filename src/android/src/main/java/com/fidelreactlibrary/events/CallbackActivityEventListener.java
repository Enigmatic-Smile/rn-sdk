package com.fidelreactlibrary.events;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;

public final class CallbackActivityEventListener
        extends BaseActivityEventListener
        implements CallbackInput {

    @Override
    public void onActivityResult(Activity activity,
                                 int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);

    }

    @Override
    public void callbackIsReady(Callback callback) {

    }
}
