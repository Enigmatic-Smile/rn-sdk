package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;

import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap>, DataOutput<Bitmap>, ConstantsProvider {

    public static final String BANNER_IMAGE_KEY = "bannerImage";
    public static final String AUTO_SCAN_KEY = "autoScan";
    public static final String COMPANY_NAME_KEY = "companyName";
    public static final String PROGRAM_NAME_KEY = "programName";
    public static final String DELETE_INSTRUCTIONS_KEY = "deleteInstructions";
    public static final String PRIVACY_URL_KEY = "privacyUrl";
    public static final String TERMS_CONDITIONS_URL_KEY = "termsConditionsUrl";
    public static final String META_DATA_KEY = "metaData";
    public static final String ALLOWED_COUNTRIES_KEY = "allowedCountries";
    public static final String DEFAULT_SELECTED_COUNTRY_KEY = "defaultSelectedCountry";
    public static final String CARD_SCHEMES_KEY = "supportedCardSchemes";
    public static final List<String> OPTION_KEYS = Collections.unmodifiableList(
            Arrays.asList(
                    BANNER_IMAGE_KEY,
                    AUTO_SCAN_KEY,
                    COMPANY_NAME_KEY,
                    PROGRAM_NAME_KEY,
                    DELETE_INSTRUCTIONS_KEY,
                    PRIVACY_URL_KEY,
                    TERMS_CONDITIONS_URL_KEY,
                    META_DATA_KEY,
                    ALLOWED_COUNTRIES_KEY,
                    CARD_SCHEMES_KEY
            ));

    private final DataProcessor<ReadableMap> imageAdapter;
    private final CountryAdapter countryAdapter;
    private final CardSchemesAdapter cardSchemesAdapter;

    public FidelOptionsAdapter(DataProcessor<ReadableMap> imageAdapter,
                               CountryAdapter countryAdapter,
                               CardSchemesAdapter cardSchemesAdapter) {
        this.imageAdapter = imageAdapter;
        this.countryAdapter = countryAdapter;
        this.cardSchemesAdapter = cardSchemesAdapter;
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
        if (valueIsValidFor(data, PROGRAM_NAME_KEY)) {
            Fidel.programName = data.getString(PROGRAM_NAME_KEY);
        }
        if (valueIsValidFor(data, DELETE_INSTRUCTIONS_KEY)) {
            Fidel.deleteInstructions = data.getString(DELETE_INSTRUCTIONS_KEY);
        }
        if (valueIsValidFor(data, PRIVACY_URL_KEY)) {
            Fidel.privacyURL = data.getString(PRIVACY_URL_KEY);
        }
        if (valueIsValidFor(data, TERMS_CONDITIONS_URL_KEY)) {
            Fidel.termsConditionsURL = data.getString(TERMS_CONDITIONS_URL_KEY);
        }
        if (valueIsValidFor(data, META_DATA_KEY)) {
            ReadableMap metaDataMap = data.getMap(META_DATA_KEY);
            if (metaDataMap != null) {
                Fidel.metaData = getJSONWithMap(metaDataMap);
            }
        }
        if (valueIsValidFor(data, ALLOWED_COUNTRIES_KEY)) {
            Fidel.allowedCountries = countryAdapter.parseAllowedCountries(data.getArray(ALLOWED_COUNTRIES_KEY));
        }
        if (valueIsValidFor(data, DEFAULT_SELECTED_COUNTRY_KEY)) {
            Fidel.defaultSelectedCountry = countryAdapter.countryWithInteger(data.getInt(DEFAULT_SELECTED_COUNTRY_KEY));
        }
        if (data.hasKey(CARD_SCHEMES_KEY)) {
            Fidel.supportedCardSchemes = cardSchemesAdapter.cardSchemesWithReadableArray(data.getArray(CARD_SCHEMES_KEY));
        }
    }

    private JSONObject getJSONWithMap(ReadableMap metaDataMap) {
        return new JSONObject(metaDataMap.toHashMap());
    }

    private boolean valueIsValidFor(ReadableMap map, String key) {
        return (map.hasKey(key) && !map.isNull(key));
    }

    @Override
    public void output(Bitmap data) {
        Fidel.bannerImage = data;
    }

    @Nonnull
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = countryAdapter.getConstants();
        constants.putAll(cardSchemesAdapter.getConstants());
        return constants;
    }
}
