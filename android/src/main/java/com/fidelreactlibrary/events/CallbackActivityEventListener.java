package com.fidelreactlibrary.events;

import android.util.Log;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.EnrollmentErrorType;
import com.fidelapi.entities.FidelError;
import com.fidelapi.entities.FidelErrorType;
import com.fidelapi.entities.FidelResult;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.DataConverter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class CallbackActivityEventListener implements CallbackInput, OnResultObserver {

    private final DataConverter<Object, WritableMap> linkResultConverter;
    private final DataProcessor<WritableMap> errorHandler;
    private Callback callback;

    public CallbackActivityEventListener(DataConverter<Object, WritableMap> linkResultConverter,
                                         DataProcessor<WritableMap> errorHandler) {
        this.linkResultConverter = linkResultConverter;
        this.errorHandler = errorHandler;
    }

    @Override
    public void callbackIsReady(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onResultAvailable(FidelResult fidelResult) {
        if (fidelResult instanceof FidelResult.Enrollment) {
            Log.d("Fidel.Debug", ((FidelResult.Enrollment) fidelResult).getEnrollmentResult().cardId);
        } else if (fidelResult instanceof FidelResult.VerificationSuccessful) {
            Log.d("Fidel.Debug", "The card verification was successful");
        } else {
            FidelError error = ((FidelResult.Error) fidelResult).getError();
            Log.e("Fidel.Debug", error.getMessage());
            if (error.type == FidelErrorType.UserCanceled.INSTANCE) {
                Log.e("Fidel.Debug", "the user cancelled the flow");
            } else if (error.type == FidelErrorType.SdkConfigurationError.INSTANCE) {
                Log.e("Fidel.Debug", "Fidel should be configured correctly");
            } else if (error.type instanceof FidelErrorType.EnrollmentError) {
                FidelErrorType.EnrollmentError enrollmentError = (FidelErrorType.EnrollmentError)error.type;
                if (enrollmentError.type == EnrollmentErrorType.CARD_ALREADY_EXISTS) {
                    Log.e("Fidel.Debug", "card was already enrolled");
                } else {
                    Log.e("Fidel.Debug", "another enrollment error");
                }
            }
        }
    }
}
