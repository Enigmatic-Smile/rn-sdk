package com.fidelreactlibrary.adapters.abstraction;

import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;

public interface CountryAdapter extends ConstantsProvider {
    Fidel.Country countryWithInteger(int integer);
    Fidel.Country[] parseAllowedCountries(ReadableArray inputArray);
}
