package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class CardSchemeAdapterStub implements CardSchemesAdapter {
    @Override
    public Fidel.CardScheme cardSchemesWithReadableArray(ReadableArray cardSchemes) {
        return null;
    }

    @NotNull
    @Override
    public Map<String, Object> getConstants() {
        return new ConstantsProviderStub("testKeyCardSchemeAdapter", 234).getConstants();
    }
}
