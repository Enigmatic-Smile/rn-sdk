package com.fidelreactlibrary.events;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;
import com.fidel.sdk.LinkResultError;
import com.fidelreactlibrary.adapters.abstraction.DataConverter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidel.sdk.data.abstraction.FidelCardLinkingObserver;

public final class CallbackActivityEventListener
        extends BaseActivityEventListener
        implements CallbackInput, FidelCardLinkingObserver {

    private final DataConverter<Object, WritableMap> linkResultConverter;
    private final DataProcessor<WritableMap> errorHandler;
    private Callback callback;

    public CallbackActivityEventListener(DataConverter<Object, WritableMap> linkResultConverter,
                                         DataProcessor<WritableMap> errorHandler) {
        this.linkResultConverter = linkResultConverter;
        this.errorHandler = errorHandler;
    }

    @Override
    public void onActivityResult(Activity activity,
                                 int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        if (requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
            LinkResult result = data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
            LinkResultError error = result.getError();
            if (error == null) {
                WritableMap convertedLinkResult = linkResultConverter.getConvertedDataFor(result);
                callback.invoke(null, convertedLinkResult);
            }
            else {
                WritableMap convertedError = linkResultConverter.getConvertedDataFor(error);
                errorHandler.process(convertedError);
            }
        }
    }

    @Override
    public void callbackIsReady(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCardLinkingFailed(LinkResultError linkResultError) {
        WritableMap convertedError = linkResultConverter.getConvertedDataFor(linkResultError);
        errorHandler.process(convertedError);
    }

    @Override
    public void onCardLinkingSucceeded(LinkResult linkResult) {
    }
}
