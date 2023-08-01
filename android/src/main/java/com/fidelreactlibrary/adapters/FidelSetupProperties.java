package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;

public enum FidelSetupProperties {
    SDK_KEY("sdkKey"),
    PROGRAM_ID("programId"),
    PROGRAM_TYPE("programType"),
    OPTIONS("options"),
    CONSENT_TEXT("consentText");

    public enum Options {
        BANNER_IMAGE("bannerImage"),
        ALLOWED_COUNTRIES("allowedCountries"),
        DEFAULT_SELECTED_COUNTRY("defaultSelectedCountry"),
        SUPPORTED_CARD_SCHEMES("supportedCardSchemes"),
        THIRD_PARTY_VERIFICATION_CHOICE("thirdPartyVerificationChoice"),
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
        COMPANY_NAME("companyName"),
        TERMS_AND_CONDITIONS_URL("termsAndConditionsUrl"),
        PRIVACY_POLICY_URL("privacyPolicyUrl"),
        PROGRAM_NAME("programName"),
        DELETE_INSTRUCTIONS("deleteInstructions");

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
    FidelSetupProperties(final @NonNull String jsName) {
        this.jsName = jsName;
    }

    public String jsName() {
        return jsName;
    }
}
