package com.fidelreactlibrary.adapters;

import com.fidel.sdk.Fidel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class FidelCountryAdapter implements CountryAdapter {

    public static final String COUNTRIES_KEY = "Country";

    private static final String UNITED_KINGDOM_COUNTRY_KEY = "unitedKingdom";
    private static final String UNITED_STATES_COUNTRY_KEY = "unitedStates";
    private static final String JAPAN_COUNTRY_KEY = "japan";
    private static final String SWEDEN_COUNTRY_KEY = "sweden";
    private static final String IRELAND_COUNTRY_KEY = "ireland";

    private static final String NOT_FOUND_COUNTRY_KEY = "notFound";

    public @NotNull String keyFor(@NotNull Fidel.Country country) {
        switch (country) {
            case UNITED_KINGDOM: return UNITED_KINGDOM_COUNTRY_KEY;
            case UNITED_STATES: return UNITED_STATES_COUNTRY_KEY;
            case JAPAN: return JAPAN_COUNTRY_KEY;
            case SWEDEN: return SWEDEN_COUNTRY_KEY;
            case IRELAND: return IRELAND_COUNTRY_KEY;
        }
        return NOT_FOUND_COUNTRY_KEY;
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        final Map<String, Integer> countriesMap = new HashMap<>();
        for (Fidel.Country country :
                Fidel.Country.values()) {
            String countryKey = keyFor(country);
            countriesMap.put(countryKey, country.ordinal());
        }
        constants.put(COUNTRIES_KEY, countriesMap);
        return constants;
    }
}
