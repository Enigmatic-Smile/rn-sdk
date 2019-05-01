package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;

import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap>, DataOutput<Bitmap>, ConstantsProvider {

    public static final String BANNER_IMAGE_KEY = "bannerImage";
    public static final String AUTO_SCAN_KEY = "autoScan";
    public static final String COMPANY_NAME_KEY = "companyName";
    public static final String DELETE_INSTRUCTIONS_KEY = "deleteInstructions";
    public static final String PRIVACY_URL_KEY = "privacyUrl";
    public static final String META_DATA_KEY = "metaData";
    public static final String COUNTRY_KEY = "country";
    public static final List<String> OPTION_KEYS = Collections.unmodifiableList(
            Arrays.asList(
                    BANNER_IMAGE_KEY,
                    AUTO_SCAN_KEY,
                    COMPANY_NAME_KEY,
                    DELETE_INSTRUCTIONS_KEY,
                    PRIVACY_URL_KEY,
                    META_DATA_KEY,
                    COUNTRY_KEY
            ));

    private final DataProcessor<ReadableMap> imageAdapter;
    private final CountryAdapter countryAdapter;

    public FidelOptionsAdapter(DataProcessor<ReadableMap> imageAdapter,
                               CountryAdapter countryAdapter) {
        this.imageAdapter = imageAdapter;
        this.countryAdapter = countryAdapter;
    }

    @Override
    public void process(ReadableMap data) {
        if (valueIsValidFor(data, BANNER_IMAGE_KEY)) {
            imageAdapter.process(data.getMap(BANNER_IMAGE_KEY));
        }
        if (valueIsValidFor(data, AUTO_SCAN_KEY)) {
            Fidel.autoScan = data.getBoolean(AUTO_SCAN_KEY);
        }
        if (valueIsValidFor(data, COMPANY_NAME_KEY)) {
            Fidel.companyName = data.getString(COMPANY_NAME_KEY);
        }
        if (valueIsValidFor(data, DELETE_INSTRUCTIONS_KEY)) {
            Fidel.deleteInstructions = data.getString(DELETE_INSTRUCTIONS_KEY);
        }
        if (valueIsValidFor(data, PRIVACY_URL_KEY)) {
            Fidel.privacyURL = data.getString(PRIVACY_URL_KEY);
        }
        if (valueIsValidFor(data, META_DATA_KEY)) {
            ReadableMap metaDataMap = data.getMap(META_DATA_KEY);
            if (metaDataMap != null) {
                JSONObject metaDataJSON = new JSONObject(metaDataMap.toHashMap());
                Fidel.metaData = metaDataJSON;
            }
        }
        if (valueIsValidFor(data, COUNTRY_KEY)) {
            int countryInt = data.getInt(COUNTRY_KEY);
            Fidel.Country adaptedCountry = countryAdapter.countryWithInteger(countryInt);
            Fidel.country = adaptedCountry;
        }
    }

    private boolean valueIsValidFor(ReadableMap map, String key) {
        return (map.hasKey(key) && !map.isNull(key));
    }

    @Override
    public void output(Bitmap data) {
        Fidel.bannerImage = data;
    }

    @Override
    public Map<String, Object> getConstants() {
        return countryAdapter.getConstants();
    }
}
