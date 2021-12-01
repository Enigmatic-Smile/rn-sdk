package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;

import java.util.Map;
import java.util.Set;

public final class CountryAdapterStub implements CountryAdapter {

    public Set<Country> countriesToReturn;
    public boolean askedToParseAllowedCountries = false;

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        ConstantsProviderStub constantsProviderStub = new ConstantsProviderStub("testKey", 2);
        return constantsProviderStub.getConstants();
    }

    @NonNull
    @Override
    public Set<Country> parseAllowedCountries(ReadableArray inputArray) {
        askedToParseAllowedCountries = true;
        return countriesToReturn;
    }
}
