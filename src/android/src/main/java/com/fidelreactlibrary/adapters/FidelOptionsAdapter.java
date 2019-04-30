package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;

import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap>, DataOutput<Bitmap> {

    public static final String BANNER_IMAGE_KEY = "bannerImage";
    public static final String AUTOSCAN_KEY = "autoScan";

    private DataProcessor<ReadableMap> imageAdapter;
    public FidelOptionsAdapter(DataProcessor<ReadableMap> imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    @Override
    public void process(ReadableMap data) {
        if (data.hasKey(BANNER_IMAGE_KEY) && !data.isNull(BANNER_IMAGE_KEY)) {
            imageAdapter.process(data.getMap(BANNER_IMAGE_KEY));
        }
        if (data.hasKey(AUTOSCAN_KEY) && !data.isNull(AUTOSCAN_KEY)) {
            Fidel.autoScan = data.getBoolean(AUTOSCAN_KEY);
        }
    }

    @Override
    public void output(Bitmap data) {
        Fidel.bannerImage = data;
    }
}
