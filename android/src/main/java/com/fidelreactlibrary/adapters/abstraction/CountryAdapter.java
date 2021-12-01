package com.fidelreactlibrary.adapters.abstraction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;

import java.util.Set;

public interface CountryAdapter extends ConstantsProvider {
    @NonNull
    Set<Country> parseAllowedCountries(@Nullable ReadableArray inputArray);
}
