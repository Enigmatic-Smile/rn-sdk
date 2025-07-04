package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
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
    private static final String NORWAY_COUNTRY_KEY = "norway";
    private static final String AUSTRALIA_COUNTRY_KEY = "australia";
    private static final String NEW_ZEALAND_COUNTRY_KEY = "newZealand";
    private static final String HONG_KONG_COUNTRY_KEY = "hongKong";
    private static final String PHILIPPINES_COUNTRY_KEY = "philippines";
    private static final String SINGAPORE_COUNTRY_KEY = "singapore";
    private static final String VIETNAM_COUNTRY_KEY = "vietnam";
    private static final String SWITZERLAND_COUNTRY_KEY = "switzerland";
    private static final String FINLAND_COUNTRY_KEY = "finland";
    private static final String DENMARK_COUNTRY_KEY = "denmark";
    private static final String BRAZIL_COUNTRY_KEY = "brazil";
    private static final String EGYPT_COUNTRY_KEY = "egypt";
    private static final String OMAN_COUNTRY_KEY = "oman";
    private static final String QATAR_COUNTRY_KEY = "qatar";
    private static final String BAHRAIN_COUNTRY_KEY = "bahrain";
    private static final String KUWAIT_COUNTRY_KEY = "kuwait";
    private static final String SAUDI_ARABIA_COUNTRY_KEY = "saudiArabia";
    private static final String JORDAN_COUNTRY_KEY = "jordan";
    private static final String SOUTH_AFRICA_COUNTRY_KEY = "southAfrica";

    private static final String NOT_FOUND_COUNTRY_KEY = "notFound";

   @NonNull
    private String keyFor(@NonNull Country country) {
        switch (country) {
            case UNITED_KINGDOM: return UNITED_KINGDOM_COUNTRY_KEY;
            case UNITED_STATES: return UNITED_STATES_COUNTRY_KEY;
            case UNITED_ARAB_EMIRATES: return UNITED_ARAB_EMIRATES_KEY;
            case JAPAN: return JAPAN_COUNTRY_KEY;
            case SWEDEN: return SWEDEN_COUNTRY_KEY;
            case IRELAND: return IRELAND_COUNTRY_KEY;
            case CANADA: return CANADA_COUNTRY_KEY;
            case NORWAY: return NORWAY_COUNTRY_KEY;
            case AUSTRALIA: return AUSTRALIA_COUNTRY_KEY;
            case NEW_ZEALAND: return NEW_ZEALAND_COUNTRY_KEY;
            case HONG_KONG: return HONG_KONG_COUNTRY_KEY;
            case PHILIPPINES: return PHILIPPINES_COUNTRY_KEY;
            case SINGAPORE: return SINGAPORE_COUNTRY_KEY;
            case VIETNAM: return VIETNAM_COUNTRY_KEY;
            case SWITZERLAND: return SWITZERLAND_COUNTRY_KEY;
            case FINLAND: return FINLAND_COUNTRY_KEY;
            case DENMARK: return DENMARK_COUNTRY_KEY;
            case BRAZIL: return BRAZIL_COUNTRY_KEY;
            case EGYPT: return EGYPT_COUNTRY_KEY;
            case OMAN: return OMAN_COUNTRY_KEY;
            case QATAR: return QATAR_COUNTRY_KEY;
            case BAHRAIN: return BAHRAIN_COUNTRY_KEY;
            case KUWAIT: return KUWAIT_COUNTRY_KEY;
            case SAUDI_ARABIA: return SAUDI_ARABIA_COUNTRY_KEY;
            case JORDAN: return JORDAN_COUNTRY_KEY;
            case SOUTH_AFRICA: return SOUTH_AFRICA_COUNTRY_KEY;
        }
        return NOT_FOUND_COUNTRY_KEY;
    }

    public @Nullable Country countryWithJSValue(String jsValue) {
        switch (jsValue) {
            case UNITED_KINGDOM_COUNTRY_KEY: return Country.UNITED_KINGDOM;
            case UNITED_STATES_COUNTRY_KEY: return Country.UNITED_STATES;
            case UNITED_ARAB_EMIRATES_KEY: return Country.UNITED_ARAB_EMIRATES;
            case JAPAN_COUNTRY_KEY: return Country.JAPAN;
            case SWEDEN_COUNTRY_KEY: return Country.SWEDEN;
            case IRELAND_COUNTRY_KEY: return Country.IRELAND;
            case CANADA_COUNTRY_KEY: return Country.CANADA;
            case NORWAY_COUNTRY_KEY: return Country.NORWAY;
            case AUSTRALIA_COUNTRY_KEY: return Country.AUSTRALIA;
            case NEW_ZEALAND_COUNTRY_KEY: return Country.NEW_ZEALAND;
            case HONG_KONG_COUNTRY_KEY: return Country.HONG_KONG;
            case PHILIPPINES_COUNTRY_KEY: return Country.PHILIPPINES;
            case SINGAPORE_COUNTRY_KEY: return Country.SINGAPORE;
            case VIETNAM_COUNTRY_KEY: return Country.VIETNAM;
            case SWITZERLAND_COUNTRY_KEY: return Country.SWITZERLAND;
            case FINLAND_COUNTRY_KEY: return Country.FINLAND;
            case DENMARK_COUNTRY_KEY: return Country.DENMARK;
            case BRAZIL_COUNTRY_KEY: return Country.BRAZIL;
            case EGYPT_COUNTRY_KEY: return Country.EGYPT;
            case OMAN_COUNTRY_KEY: return Country.OMAN;
            case QATAR_COUNTRY_KEY: return Country.QATAR;
            case BAHRAIN_COUNTRY_KEY: return Country.BAHRAIN;
            case KUWAIT_COUNTRY_KEY: return Country.KUWAIT;
            case SAUDI_ARABIA_COUNTRY_KEY: return Country.SAUDI_ARABIA;
            case JORDAN_COUNTRY_KEY: return Country.JORDAN;
            case SOUTH_AFRICA_COUNTRY_KEY: return Country.SOUTH_AFRICA;
        }
        return null;
    }

    @NonNull
    @Override
    public String jsCountryValue(@NonNull Country country) {
        return keyFor(country);
    }

    @Override
    public @NonNull Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        final Map<String, String> countriesMap = new HashMap<>();
        for (Country country :
                Country.values()) {
            String countryKey = keyFor(country);
            countriesMap.put(countryKey, countryKey);
        }
        constants.put(EXPORTED_COUNTRY_KEY, countriesMap);
        return constants;
    }

    @Override
    public @NonNull Set<Country> parseAllowedCountries(@Nullable ReadableArray inputArray) {
        Set<Country> countries = EnumSet.noneOf(Country.class);
        if (inputArray == null) {
            return countries;
        }
        for (int i = 0; i < inputArray.size(); i++) {
            if (inputArray.getType(i) != ReadableType.String) {
                continue;
            }
            Country country = countryWithJSValue(inputArray.getString(i));
            if (country != null) {
                countries.add(country);
            }
        }
        return countries;
    }
}
