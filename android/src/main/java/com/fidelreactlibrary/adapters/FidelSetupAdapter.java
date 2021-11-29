package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelSetupAdapter implements DataProcessor<ReadableMap> {

    @Override
    public void process(ReadableMap data) {
        Fidel.sdkKey = data.getString(FidelSetupKeys.SDK_KEY.jsName());
        Fidel.programId = data.getString(FidelSetupKeys.PROGRAM_ID.jsName());
        Fidel.companyName = data.getString(FidelSetupKeys.COMPANY_NAME.jsName());
    }
}
