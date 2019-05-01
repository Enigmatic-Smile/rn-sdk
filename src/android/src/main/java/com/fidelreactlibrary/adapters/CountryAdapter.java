package com.fidelreactlibrary.adapters;

import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;

public interface CountryAdapter extends ConstantsProvider {
    String COUNTRY_KEY = "Country";
    Fidel.Country countryWithInteger(int integer);
}
