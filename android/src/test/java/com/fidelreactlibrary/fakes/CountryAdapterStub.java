package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.Fidel.Country;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;

import java.util.Map;

import javax.annotation.Nonnull;

public final class CountryAdapterStub implements CountryAdapter {

    public Country[] countriesToReturn;

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
    public Country[] parseAllowedCountries(ReadableArray inputArray) {
        return new Fidel.Country[]{Fidel.Country.UNITED_KINGDOM, Fidel.Country.JAPAN, Fidel.Country.CANADA};
    }
}
