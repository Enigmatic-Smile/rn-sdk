package com.fidelreactlibrary.adapters;

import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;

public interface CountryAdapter extends ConstantsProvider {
    Fidel.Country countryWithInteger(int integer);
}
