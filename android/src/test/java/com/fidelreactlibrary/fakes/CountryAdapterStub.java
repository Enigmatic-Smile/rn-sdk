package com.fidelreactlibrary.fakes;

import com.fidel.sdk.Fidel.Country;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;

import java.util.Map;

public final class CountryAdapterStub implements CountryAdapter {

    public Country countryToReturn;
    public int countryIntegerReceived;

    @Override
    public Map<String, Object> getConstants() {
        ConstantsProviderStub constantsProviderStub = new ConstantsProviderStub("testKey", 2);
        return constantsProviderStub.getConstants();
    }

    @Override
    public Country countryWithInteger(int integer) {
        countryIntegerReceived = integer;
        return countryToReturn;
    }
}
