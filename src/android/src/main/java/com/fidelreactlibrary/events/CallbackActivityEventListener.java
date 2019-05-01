package com.fidelreactlibrary.events;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;
import com.fidel.sdk.LinkResultErrorCode;
import com.fidelreactlibrary.adapters.abstraction.DataConverter;

public final class CallbackActivityEventListener
        extends BaseActivityEventListener
        implements CallbackInput {

    private final DataConverter<LinkResult, ReadableMap> linkResultConvertor;
    private Callback callback;

    public CallbackActivityEventListener(DataConverter<LinkResult, ReadableMap> linkResultConvertor) {
        this.linkResultConvertor = linkResultConvertor;
    }

    @Override
    public void onActivityResult(Activity activity,
                                 int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        LinkResult linkResult = data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
        ReadableMap convertedLinkResult = linkResultConvertor.getConvertedDataFor(linkResult);
        callback.invoke(null, convertedLinkResult);
    }

    @Override
    public void callbackIsReady(Callback callback) {
        this.callback = callback;
    }
}
