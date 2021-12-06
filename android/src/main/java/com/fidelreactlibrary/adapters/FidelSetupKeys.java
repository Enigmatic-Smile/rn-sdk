package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;

public enum FidelSetupKeys {
    SDK_KEY("sdkKey"),
    PROGRAM_ID("programId"),
    COMPANY_NAME("companyName"),
    OPTIONS("options"),
    CONSENT_TEXT("consentText");

    public enum Options {
        BANNER_IMAGE("bannerImage"),
        ALLOWED_COUNTRIES("allowedCountries"),
        SUPPORTED_CARD_SCHEMES("supportedCardSchemes"),
        SHOULD_AUTO_SCAN("shouldAutoScanCard"),
        META_DATA("metaData");

        private final @NonNull String jsName;

        /**
         * @param jsName The name of the key that will be available in JavaScript
         */
        Options(final @NonNull String jsName) {
            this.jsName = jsName;
        }

        public String jsName() {
            return jsName;
        }
    }

    public enum ConsentText {
        TERMS_AND_CONDITIONS_URL("termsAndConditionsUrl");

        private final @NonNull String jsName;

        /**
         * @param jsName The name of the key that will be available in JavaScript
         */
        ConsentText(final @NonNull String jsName) {
            this.jsName = jsName;
        }

        public String jsName() {
            return jsName;
        }
    }

    private final @NonNull String jsName;

    /**
     * @param jsName The name of the key that will be available in JavaScript
     */
    FidelSetupKeys(final @NonNull String jsName) {
        this.jsName = jsName;
    }

    public String jsName() {
        return jsName;
    }
}
