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
            id = getStringParam(data, CardVerificationConfigurationProperties.ID, "");
            consentId = getStringParam(data, CardVerificationConfigurationProperties.CONSENT_ID, "");
            last4Digits = getStringParam(data, CardVerificationConfigurationProperties.LAST_4_DIGITS, null);
        }
        return new CardVerificationConfiguration(id, consentId, last4Digits);
    }

    private static String getStringParam(ReadableMap data, CardVerificationConfigurationProperties key,
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
