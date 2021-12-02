package com.fidelreactlibrary.adapters.abstraction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.CardScheme;

import java.util.Set;

public interface CardSchemesAdapter extends ConstantsProvider {
    @NonNull Set<CardScheme> cardSchemesWithReadableArray(@Nullable ReadableArray cardSchemes);
}
