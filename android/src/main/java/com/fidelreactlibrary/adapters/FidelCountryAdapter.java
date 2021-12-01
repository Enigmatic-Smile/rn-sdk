package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class FidelCountryAdapter implements CountryAdapter {

    private static final String EXPORTED_COUNTRY_KEY = "Country";

    private static final String UNITED_KINGDOM_COUNTRY_KEY = "unitedKingdom";
    private static final String UNITED_STATES_COUNTRY_KEY = "unitedStates";
    private static final String UNITED_ARAB_EMIRATES_KEY = "unitedArabEmirates";
    private static final String JAPAN_COUNTRY_KEY = "japan";
    private static final String SWEDEN_COUNTRY_KEY = "sweden";
    private static final String IRELAND_COUNTRY_KEY = "ireland";
    private static final String CANADA_COUNTRY_KEY = "canada";

    private static final String NOT_FOUND_COUNTRY_KEY = "notFound";

    public @NonNull String keyFor(@NonNull Country country) {
        switch (country) {
            case UNITED_KINGDOM: return UNITED_KINGDOM_COUNTRY_KEY;
            case UNITED_STATES: return UNITED_STATES_COUNTRY_KEY;
            case UNITED_ARAB_EMIRATES: return UNITED_ARAB_EMIRATES_KEY;
            case JAPAN: return JAPAN_COUNTRY_KEY;
            case SWEDEN: return SWEDEN_COUNTRY_KEY;
            case IRELAND: return IRELAND_COUNTRY_KEY;
            case CANADA: return CANADA_COUNTRY_KEY;
        }
        return NOT_FOUND_COUNTRY_KEY;
    }

    @Override
    public @NonNull Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        final Map<String, Integer> countriesMap = new HashMap<>();
        for (Country country :
                Country.values()) {
            String countryKey = keyFor(country);
            countriesMap.put(countryKey, country.ordinal());
        }
        constants.put(EXPORTED_COUNTRY_KEY, countriesMap);
        return constants;
    }

    private @Nullable Country countryWithInteger(int integer) {
        if (integer < Country.values().length) {
            return Country.values()[integer];
        }
        return null;
    }

    @Override
    public @NonNull Set<Country> parseAllowedCountries(@Nullable ReadableArray inputArray) {
        Set<Country> countries = EnumSet.noneOf(Country.class);
        if (inputArray == null) {
            return countries;
        }
        for (int i = 0; i < inputArray.size(); i++) {
            Country country = countryWithInteger(inputArray.getInt(i));
            if (country != null) {
                countries.add(country);
            }
        }
        return countries;
    }
}
