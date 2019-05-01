package com.fidelreactlibrary.adapters.abstraction;

import com.fidel.sdk.Fidel;

public interface CountryAdapter extends ConstantsProvider {
    Fidel.Country countryWithInteger(int integer);
}
