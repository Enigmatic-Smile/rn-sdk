package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;

import java.util.Map;
import java.util.Set;

public final class CountryAdapterStub implements CountryAdapter {

    public Set<Country> countriesToReturn;
    public boolean askedToParseAllowedCountries = false;
    public String jsValueToReturn;
    public Country countryForJSValueToReturn;
    public String countryJSValueReceived;

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        ConstantsProviderStub constantsProviderStub = new ConstantsProviderStub("testKey", 2);
        return constantsProviderStub.getConstants();
    }

    @Nullable
    @Override
    public Country countryWithJSValue(String jsValue) {
        countryJSValueReceived = jsValue;
        return countryForJSValueToReturn;
    }

    @NonNull
    @Override
    public Set<Country> parseAllowedCountries(ReadableArray inputArray) {
        askedToParseAllowedCountries = true;
        return countriesToReturn;
    }

    @NonNull
    @Override
    public String jsCountryValue(@NonNull Country country) {
        return jsValueToReturn;
    }
}
