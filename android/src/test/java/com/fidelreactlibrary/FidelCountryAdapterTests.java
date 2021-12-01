package com.fidelreactlibrary;

import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;
import com.fidelreactlibrary.fakes.ReadableArrayStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

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
    public void test_WhenCountryOrdinalNumberIsTooHigh_ReturnEmptyCountriesSet() {
        int[] countriesArrayValues = new int[]{1234};
        ReadableArrayStub countriesArrayStub = new ReadableArrayStub(1, countriesArrayValues);
        assertTrue(sut.parseAllowedCountries(countriesArrayStub).isEmpty());
    }

    @Test
    public void test_WhenParsingNullReadableArray_ReturnEmptyCountriesSet() {
        assertTrue(sut.parseAllowedCountries(null).isEmpty());
    }

    @Test
    public void test_WhenParsingEmptyReadableArray_ReturnEmptyCountriesSet() {
        assertTrue(sut.parseAllowedCountries(new ReadableArrayStub(0, new int[]{})).isEmpty());
    }

    @Test
    public void test_WhenCountryOrdinalNumberIsValid_ReturnCountryFromThatOrdinalNumber() {
        int[] countriesArrayValues = new int[]{Country.CANADA.ordinal(), Country.UNITED_ARAB_EMIRATES.ordinal(), Country.UNITED_KINGDOM.ordinal(),
                Country.SWEDEN.ordinal(), Country.UNITED_STATES.ordinal(), Country.IRELAND.ordinal(), Country.JAPAN.ordinal()};
        ReadableArrayStub countriesArrayStub = new ReadableArrayStub(countriesArrayValues.length, countriesArrayValues);

        Set<Country> expectedCountries = EnumSet.of(Country.CANADA, Country.UNITED_ARAB_EMIRATES, Country.UNITED_KINGDOM,
                Country.SWEDEN, Country.UNITED_STATES, Country.IRELAND, Country.JAPAN);
        Set<Country> adaptedCountries = sut.parseAllowedCountries(countriesArrayStub);

        assertEquals(countriesArrayValues.length, adaptedCountries.size());
        assertEquals(adaptedCountries, expectedCountries);
    }

    private void assertCorrectCountryValueFor(String key,
                                              Country countryValue,
                                              Map<String, Integer> map) {
        assertNotEquals(key, "notFound");
        assertThat(map.keySet(), hasItem(key));
        assertEquals(map.get(key).intValue(), countryValue.ordinal());
    }
}
