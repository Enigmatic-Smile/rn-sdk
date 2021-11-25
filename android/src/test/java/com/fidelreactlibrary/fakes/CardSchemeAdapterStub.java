package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.CardScheme;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

public final class CardSchemeAdapterStub implements CardSchemesAdapter {

    public ReadableArray cardSchemesReceived;
    public Set<CardScheme> fakeAdaptedCardSchemesToReturn;

    @Override
    public Set<CardScheme> cardSchemesWithReadableArray(ReadableArray cardSchemes) {
        cardSchemesReceived = cardSchemes;
        return fakeAdaptedCardSchemesToReturn;
    }

    @Nonnull
    @Override
    public Map<String, Object> getConstants() {
        return new ConstantsProviderStub("testKeyCardSchemeAdapter", 234).getConstants();
    }
}
