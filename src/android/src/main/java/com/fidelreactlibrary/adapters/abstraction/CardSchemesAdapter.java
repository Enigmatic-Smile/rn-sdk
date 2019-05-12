package com.fidelreactlibrary.adapters.abstraction;

import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;

public interface CardSchemesAdapter extends ConstantsProvider {
    Fidel.CardScheme cardSchemesWithReadableArray(ReadableArray cardSchemes);
}
