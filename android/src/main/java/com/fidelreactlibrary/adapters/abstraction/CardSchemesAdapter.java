package com.fidelreactlibrary.adapters.abstraction;

import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;

import java.util.Set;

public interface CardSchemesAdapter extends ConstantsProvider {
    Set<Fidel.CardScheme> cardSchemesWithReadableArray(ReadableArray cardSchemes);
}
