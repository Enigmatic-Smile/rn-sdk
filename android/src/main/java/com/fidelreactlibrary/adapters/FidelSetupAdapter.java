package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelSetupAdapter implements DataProcessor<ReadableMap>, DataOutput<Bitmap> {

    private final DataProcessor<ReadableMap> imageAdapter;

    public FidelSetupAdapter(DataProcessor<ReadableMap> imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    @Override
    public void process(ReadableMap data) {
        Fidel.sdkKey = data.getString(FidelSetupKeys.SDK_KEY.jsName());
        Fidel.programId = data.getString(FidelSetupKeys.PROGRAM_ID.jsName());
        Fidel.companyName = data.getString(FidelSetupKeys.COMPANY_NAME.jsName());
        ReadableMap optionsMap = data.getMap(FidelSetupKeys.OPTIONS.jsName());
        if (optionsMap != null) {
            imageAdapter.process(optionsMap.getMap(FidelSetupKeys.Options.BANNER_IMAGE.jsName()));
        }
    }

    @Override
    public void output(Bitmap data) {
        Fidel.bannerImage = data;
    }
}
