package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

public final class FidelCardSchemesAdapter implements CardSchemesAdapter {

    private static final String EXPORTED_CARD_SCHEME_KEY = "CardScheme";

    private static final String VISA_CARD_SCHEME_VALUE = "visa";
    private static final String MASTERCARD_SCHEME_VALUE = "mastercard";
    private static final String AMERICAN_EXPRESS_CARD_SCHEME_VALUE = "americanExpress";

    @Override
    public @Nonnull Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        Map<String, String> cardSchemesConstants = new HashMap<>();
        for (CardScheme cardScheme : CardScheme.values()) {
            String cardSchemeKey = jsValue(cardScheme);
            cardSchemesConstants.put(cardSchemeKey, cardSchemeKey);
        }
        constants.put(EXPORTED_CARD_SCHEME_KEY, cardSchemesConstants);
        return constants;
    }

    @NonNull
    @Override
    public String jsValue(@NonNull CardScheme cardScheme) {
        switch (cardScheme) {
            case VISA: return VISA_CARD_SCHEME_VALUE;
            case MASTERCARD: return MASTERCARD_SCHEME_VALUE;
            case AMERICAN_EXPRESS: return AMERICAN_EXPRESS_CARD_SCHEME_VALUE;
        }
        return "notFound";
    }

    @Nullable
    private CardScheme cardSchemeWithJsValue(String jsValue) {
        switch (jsValue) {
            case VISA_CARD_SCHEME_VALUE: return CardScheme.VISA;
            case MASTERCARD_SCHEME_VALUE: return CardScheme.MASTERCARD;
            case AMERICAN_EXPRESS_CARD_SCHEME_VALUE: return CardScheme.AMERICAN_EXPRESS;
        }
        return null;
    }

    @Override
    public @NonNull Set<CardScheme> cardSchemesWithReadableArray(@Nullable ReadableArray arrayToAdapt) {
        Set<CardScheme> cardSchemeSet = EnumSet.noneOf(CardScheme.class);
        if (arrayToAdapt == null) {
            return cardSchemeSet;
        }
        for (int i = 0; i < arrayToAdapt.size(); i++) {
            if (arrayToAdapt.getType(i) != ReadableType.String) {
                continue;
            }
            CardScheme cardScheme = cardSchemeWithJsValue(arrayToAdapt.getString(i));
            if (cardScheme != null) {
                cardSchemeSet.add(cardScheme);
            }
        }
        return cardSchemeSet;
    }
}
