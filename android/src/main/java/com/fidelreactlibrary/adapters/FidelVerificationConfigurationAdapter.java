package com.fidelreactlibrary.adapters;

import androidx.annotation.Nullable;

import com.fidelapi.entities.CardVerificationConfiguration;
import com.fidelreactlibrary.adapters.abstraction.VerificationConfigurationAdapter;
import com.facebook.react.bridge.ReadableMap;

public class FidelVerificationConfigurationAdapter implements VerificationConfigurationAdapter {

    @Override
    public CardVerificationConfiguration adapt(ReadableMap data) {
        String id = "";
        String consentId = "";
        String last4Digits = null;
        if (data != null) {
            id = getStringParam(data, CardVerificationConfigurationKeys.ID, "");
            consentId = getStringParam(data, CardVerificationConfigurationKeys.CONSENT_ID, "");
            last4Digits = getStringParam(data, CardVerificationConfigurationKeys.LAST_4_DIGITS, null);
        }
        return new CardVerificationConfiguration(id, consentId, last4Digits);
    }

    private static String getStringParam(ReadableMap data, CardVerificationConfigurationKeys key,
            @Nullable String defaultValue) {
        String value = defaultValue;
        if (data.hasKey(key.jsName())) {
            value = data.getString(key.jsName());
            if (value == null) {
                value = defaultValue;
            }
        }
        return value;
    }
}
