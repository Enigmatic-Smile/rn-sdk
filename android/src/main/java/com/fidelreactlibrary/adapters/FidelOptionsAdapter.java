package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap>, ConstantsProvider {

    public static final String SHOULD_AUTO_SCAN_KEY = "shouldAutoScanCard";
    public static final String PROGRAM_NAME_KEY = "programName";
    public static final String DELETE_INSTRUCTIONS_KEY = "deleteInstructions";
    public static final String PRIVACY_POLICY_URL_KEY = "privacyPolicyUrl";
    public static final String TERMS_CONDITIONS_URL_KEY = "termsAndConditionsUrl";
    public static final String META_DATA_KEY = "metaData";
    public static final String ALLOWED_COUNTRIES_KEY = "allowedCountries";
    public static final String CARD_SCHEMES_KEY = "supportedCardSchemes";
    public static final List<String> OPTION_KEYS = Collections.unmodifiableList(
            Arrays.asList(
                    SHOULD_AUTO_SCAN_KEY,
                    PROGRAM_NAME_KEY,
                    DELETE_INSTRUCTIONS_KEY,
                    PRIVACY_POLICY_URL_KEY,
                    TERMS_CONDITIONS_URL_KEY,
                    META_DATA_KEY,
                    ALLOWED_COUNTRIES_KEY,
                    CARD_SCHEMES_KEY
            ));

    private final CountryAdapter countryAdapter;
    private final CardSchemesAdapter cardSchemesAdapter;

    public FidelOptionsAdapter(CountryAdapter countryAdapter,
                               CardSchemesAdapter cardSchemesAdapter) {
        this.countryAdapter = countryAdapter;
        this.cardSchemesAdapter = cardSchemesAdapter;
    }

    @Override
    public void process(ReadableMap data) {
        if (valueIsValidFor(data, SHOULD_AUTO_SCAN_KEY)) {
            Fidel.shouldAutoScanCard = data.getBoolean(SHOULD_AUTO_SCAN_KEY);
        }
        if (valueIsValidFor(data, PROGRAM_NAME_KEY)) {
            Fidel.programName = data.getString(PROGRAM_NAME_KEY);
        }
        if (valueIsValidFor(data, DELETE_INSTRUCTIONS_KEY)) {
            Fidel.deleteInstructions = data.getString(DELETE_INSTRUCTIONS_KEY);
        }
        if (valueIsValidFor(data, PRIVACY_POLICY_URL_KEY)) {
            Fidel.privacyPolicyUrl = data.getString(PRIVACY_POLICY_URL_KEY);
        }
        if (valueIsValidFor(data, TERMS_CONDITIONS_URL_KEY)) {
            Fidel.termsAndConditionsUrl = data.getString(TERMS_CONDITIONS_URL_KEY);
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

    @Nonnull
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = countryAdapter.getConstants();
        constants.putAll(cardSchemesAdapter.getConstants());
        return constants;
    }
}
