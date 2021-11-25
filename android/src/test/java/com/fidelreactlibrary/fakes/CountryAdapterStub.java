package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

public final class CountryAdapterStub implements CountryAdapter {

    public Set<Country> countriesToReturn;

    @Nonnull
    @Override
    public Map<String, Object> getConstants() {
        ConstantsProviderStub constantsProviderStub = new ConstantsProviderStub("testKey", 2);
        return constantsProviderStub.getConstants();
    }

    @Override
    public Country countryWithInteger(int integer) {
        return null;
    }

    @Override
    public Set<Country> parseAllowedCountries(ReadableArray inputArray) {
        return countriesToReturn;
    }
}
