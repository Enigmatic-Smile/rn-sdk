package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelSetupAdapter implements DataProcessor<ReadableMap> {
    public static final String SDK_KEY = "sdkKey";
    public static final String PROGRAM_ID_KEY = "programId";

    @Override
    public void process(ReadableMap data) {
        Fidel.sdkKey = data.getString(SDK_KEY);
        Fidel.programId = data.getString(PROGRAM_ID_KEY);
    }
}
