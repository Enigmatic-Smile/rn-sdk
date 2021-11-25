package com.fidelreactlibrary.adapters.abstraction;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.CardScheme;

import java.util.Set;

public interface CardSchemesAdapter extends ConstantsProvider {
    Set<CardScheme> cardSchemesWithReadableArray(ReadableArray cardSchemes);
}
