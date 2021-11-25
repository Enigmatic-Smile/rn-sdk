package com.fidelreactlibrary.adapters.abstraction;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;

import java.util.Set;

public interface CountryAdapter extends ConstantsProvider {
    Country countryWithInteger(int integer);
    Set<Country> parseAllowedCountries(ReadableArray inputArray);
}
