package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public final class FidelOptionsAdapter implements DataProcessor<ReadableMap> {

    public static final String PROGRAM_NAME_KEY = "programName";
    public static final String DELETE_INSTRUCTIONS_KEY = "deleteInstructions";
    public static final String PRIVACY_POLICY_URL_KEY = "privacyPolicyUrl";
    public static final String TERMS_CONDITIONS_URL_KEY = "termsAndConditionsUrl";

    @Override
    public void process(ReadableMap data) {
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
    }

    private boolean valueIsValidFor(ReadableMap map, String key) {
        return (map.hasKey(key) && !map.isNull(key));
    }
}
