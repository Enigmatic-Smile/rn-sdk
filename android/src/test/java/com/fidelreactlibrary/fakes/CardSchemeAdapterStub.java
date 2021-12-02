package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.CardScheme;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;

import java.util.Map;
import java.util.Set;

public final class CardSchemeAdapterStub implements CardSchemesAdapter {

    public ReadableArray cardSchemesReceived;
    public Set<CardScheme> fakeAdaptedCardSchemesToReturn;
    public boolean askedToAdaptCardSchemes = false;

    @NonNull
    @Override
    public Set<CardScheme> cardSchemesWithReadableArray(@Nullable ReadableArray cardSchemes) {
        askedToAdaptCardSchemes = true;
        cardSchemesReceived = cardSchemes;
        return fakeAdaptedCardSchemesToReturn;
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        return new ConstantsProviderStub("testKeyCardSchemeAdapter", 234).getConstants();
    }
}
