package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardVerificationChoice;
import com.fidelapi.utils.FidelExperimental;
import com.fidelreactlibrary.adapters.abstraction.CardVerificationChoiceAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

@OptIn(markerClass = FidelExperimental.class)
public class FidelCardVerificationChoiceAdapter implements CardVerificationChoiceAdapter, ConstantsProvider {
    protected static final String CARD_VERIFICATION_ON_THE_SPOT = "onTheSpot";
    protected static final String CARD_VERIFICATION_DELEGATED_TO_THIRD_PARTY = "delegatedToThirdParty";
    public static final String CARD_VERIFICATION_CHOICE = "CardVerificationChoice";
    private final ObjectFactory<WritableMap> writableMapFactory;

    public FidelCardVerificationChoiceAdapter(ObjectFactory<WritableMap> writableMapFactory) {
        this.writableMapFactory = writableMapFactory;
    }

    @Override
    public ReadableMap adapt(@NonNull CardVerificationChoice cardVerificationChoice) {
        WritableMap map = writableMapFactory.create();
        map.putString(CARD_VERIFICATION_CHOICE, jsValue(cardVerificationChoice));
        return map;
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        final Map<String, String> verificationChoicesMap = new HashMap<>();
        for (CardVerificationChoice choice : CardVerificationChoice.values()) {
            String verificationChoiceKeyAndValue = jsValue(choice);
            verificationChoicesMap.put(verificationChoiceKeyAndValue, verificationChoiceKeyAndValue);
        }
        constants.put(CARD_VERIFICATION_CHOICE, verificationChoicesMap);
        return constants;
    }

    private String jsValue(@NonNull CardVerificationChoice cardVerificationChoice) {
        switch (cardVerificationChoice) {
            case DELEGATED_TO_THIRD_PARTY:
                return CARD_VERIFICATION_DELEGATED_TO_THIRD_PARTY;
            case ON_THE_SPOT:
                return CARD_VERIFICATION_ON_THE_SPOT;
        }
        return "notFound";
    }
}
