package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelSetupAdapter implements DataProcessor<ReadableMap> {
    public static final String API_KEY = "apiKey";
    public static final String PROGRAM_ID_KEY = "programId";

    @Override
    public void process(ReadableMap data) {
        Fidel.apiKey = data.getString(API_KEY);
        Fidel.programId = data.getString(PROGRAM_ID_KEY);
    }
}
