package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;

public enum CardVerificationConfigurationProperties {
    ID("id"),
    CONSENT_ID("consentId"),
    LAST_4_DIGITS("last4Digits");

    private final @NonNull String jsName;

    /**
     * @param jsName The name of the key that will be available in JavaScript
     */
    CardVerificationConfigurationProperties(final @NonNull String jsName) {
        this.jsName = jsName;
    }

    public String jsName() {
        return jsName;
    }
}
