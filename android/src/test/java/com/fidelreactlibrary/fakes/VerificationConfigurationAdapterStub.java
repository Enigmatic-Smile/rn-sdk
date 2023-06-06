package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.entities.CardVerificationConfiguration;
import com.fidelreactlibrary.adapters.abstraction.VerificationConfigurationAdapter;

public class VerificationConfigurationAdapterStub implements VerificationConfigurationAdapter {

    @Override
    public CardVerificationConfiguration adapt(ReadableMap data) {
        return new CardVerificationConfiguration("", "", null);
    }
}
