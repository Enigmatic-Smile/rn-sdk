package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap> {

    public static final String DELETE_INSTRUCTIONS_KEY = "deleteInstructions";

    @Override
    public void process(ReadableMap data) {
        if (valueIsValidFor(data, DELETE_INSTRUCTIONS_KEY)) {
            Fidel.deleteInstructions = data.getString(DELETE_INSTRUCTIONS_KEY);
        }
    }

    private boolean valueIsValidFor(ReadableMap map, String key) {
        return (map.hasKey(key) && !map.isNull(key));
    }
}
