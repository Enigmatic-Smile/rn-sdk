package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.ReadableMap;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class DataProcessorSpy implements DataProcessor<ReadableMap> {

    public ReadableMap dataToProcess;
    public Boolean hasAskedToProcessData = false;

    @Override
    public void process(ReadableMap data) {
        hasAskedToProcessData = true;
        dataToProcess = data;
    }
}
