package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class FidelCardSchemesAdapter implements CardSchemesAdapter {

    private static final String EXPORTED_CARD_SCHEME_KEY = "CardScheme";

    private static final String VISA_CARD_SCHEME_VALUE = "visa";
    private static final String MASTERCARD_SCHEME_VALUE = "mastercard";
    private static final String AMERICAN_EXPRESS_CARD_SCHEME_VALUE = "americanExpress";

    @Override
    public @NotNull Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        Map<String, Object> cardSchemesConstants = new HashMap<>();
        for (Fidel.CardScheme cardScheme : Fidel.CardScheme.values()) {
            String cardSchemeKey = null;
            switch (cardScheme) {
                case VISA: cardSchemeKey = VISA_CARD_SCHEME_VALUE; break;
                case MASTERCARD: cardSchemeKey = MASTERCARD_SCHEME_VALUE; break;
                case AMERICAN_EXPRESS: cardSchemeKey = AMERICAN_EXPRESS_CARD_SCHEME_VALUE; break;
            }
            cardSchemesConstants.put(cardSchemeKey, cardScheme.ordinal());
        }
        constants.put(EXPORTED_CARD_SCHEME_KEY, cardSchemesConstants);
        return constants;
    }

    @Override
    public Set<Fidel.CardScheme> cardSchemesWithReadableArray(ReadableArray cardSchemes) {
        return null;
    }
}
