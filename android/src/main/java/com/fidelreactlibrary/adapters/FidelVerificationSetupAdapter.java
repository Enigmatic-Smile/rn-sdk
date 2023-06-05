package com.fidelreactlibrary.adapters;

import com.fidelapi.Fidel;
import com.fidelapi.entities.CardVerificationConfiguration;
import com.fidelreactlibrary.adapters.abstraction.VerificationSetupAdapter;

public class FidelVerificationSetupAdapter implements VerificationSetupAdapter {
    @Override
    public CardVerificationConfiguration map(ReadableMap data) {
        String id = "";
        String consentId = "";
        String last4Digits = "";
        ReadableMap optionsMap = data.getMap(FidelSetupKeys.CARD_CONFIG.jsName());
        if (optionsMap != null) {
            if (optionsMap.hasKey(FidelSetupKeys.CardConfig.ID.jsName())) {
                id = optionsMap.getString(FidelSetupKeys.CardConfig.ID.jsName());
                if (id == null) {
                    id = "";
                }
            }
            if (optionsMap.hasKey(FidelSetupKeys.CardConfig.CONSENT_ID.jsName())) {
                consentId = optionsMap.getString(FidelSetupKeys.CardConfig.CONSENT_ID.jsName());
                if (consentId == null) {
                    consentId = "";
                }
            }
            if (optionsMap.hasKey(FidelSetupKeys.CardConfig.LAST_4_DIGITS.jsName())) {
                last4Digits = optionsMap.getString(FidelSetupKeys.CardConfig.LAST_4_DIGITS.jsName());
            }
        }
        return new CardVerificationConfiguration(id, consentId, last4Digits);
    }
}
