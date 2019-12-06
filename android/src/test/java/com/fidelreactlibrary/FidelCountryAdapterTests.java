package com.fidelreactlibrary;

import com.fidel.sdk.Fidel.Country;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class FidelCountryAdapterTests {

    private static final String COUNTRIES_KEY = "Country";
    
    private FidelCountryAdapter sut;
    
    @Before
    public final void setUp() {
        sut = new FidelCountryAdapter();
    }
    
    @After
    public final void tearDown() {
        sut = null;
    }

    @Test
    public void test_ToExposeCountryConstants() {
        Map<String, Object> exposedConstants = sut.getConstants();
        assertThat(exposedConstants.keySet(), hasItem(COUNTRIES_KEY));
        Map<String, Integer> countriesMap = (Map<String, Integer>)exposedConstants.get(COUNTRIES_KEY);
        assertNotNull(countriesMap);
        for (Country country : Country.values()) {
            String countryKey = sut.keyFor(country);
            assertCorrectCountryValueFor(countryKey, country, countriesMap);
        }
    }


    @Test
    public void test_WhenAskedForKeyForCountry_ItProvidesIt() {
        for (Country country : Country.values()) {
            assertNotNull(sut.keyFor(country));
        }
    }

    @Test
    public void test_WhenCountryOrdinalNumberIsTooHigh_ReturnNullCountry() {
        assertNull(sut.countryWithInteger(1239));
    }

    @Test
    public void test_WhenCountryOrdinalNumberIsValid_ReturnCountryFromThatOrdinalNumber() {
        Country[] countries = Country.values();
        for (int countryIndex = 0; countryIndex < countries.length; countryIndex++) {
            assertEquals(sut.countryWithInteger(countryIndex), countries[countryIndex]);
        }
    }

    private void assertCorrectCountryValueFor(String key,
                                              Country countryValue,
                                              Map<String, Integer> map) {
        assertNotEquals(key, "notFound");
        assertThat(map.keySet(), hasItem(key));
        assertEquals(map.get(key).intValue(), countryValue.ordinal());
    }
}
