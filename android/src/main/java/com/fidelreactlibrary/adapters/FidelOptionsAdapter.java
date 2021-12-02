package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap> {

    public static final String SHOULD_AUTO_SCAN_KEY = "shouldAutoScanCard";
    public static final String PROGRAM_NAME_KEY = "programName";
    public static final String DELETE_INSTRUCTIONS_KEY = "deleteInstructions";
    public static final String PRIVACY_POLICY_URL_KEY = "privacyPolicyUrl";
    public static final String TERMS_CONDITIONS_URL_KEY = "termsAndConditionsUrl";
    public static final String META_DATA_KEY = "metaData";
    public static final List<String> OPTION_KEYS = Collections.unmodifiableList(
            Arrays.asList(
                    SHOULD_AUTO_SCAN_KEY,
                    PROGRAM_NAME_KEY,
                    DELETE_INSTRUCTIONS_KEY,
                    PRIVACY_POLICY_URL_KEY,
                    TERMS_CONDITIONS_URL_KEY,
                    META_DATA_KEY
            ));

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
    }

    private JSONObject getJSONWithMap(ReadableMap metaDataMap) {
        return new JSONObject(metaDataMap.toHashMap());
    }

    private boolean valueIsValidFor(ReadableMap map, String key) {
        return (map.hasKey(key) && !map.isNull(key));
    }
}
