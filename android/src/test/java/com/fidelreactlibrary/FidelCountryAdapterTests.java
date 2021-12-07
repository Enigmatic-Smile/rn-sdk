package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;
import com.fidelreactlibrary.fakes.ReadableArrayStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

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
        assertTrue(exposedConstants.containsKey(COUNTRIES_KEY));
        Map<String, String> countriesMap = (Map<String, String>)exposedConstants.get(COUNTRIES_KEY);
        assertNotNull(countriesMap);
        for (Country country : Country.values()) {
            String countryKey = sut.jsCountryValue(country);
            assertCorrectCountryValueFor(countryKey, country, countriesMap);
        }
    }

    @Test
    public void test_WhenAskedForAnyCountryJSValue_ItProvidesIt() {
        for (Country country : Country.values()) {
            assertNotNull(sut.jsCountryValue(country));
        }
    }

    @Test
    public void test_WhenAskedForCanadaCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("canada", sut.jsCountryValue(Country.CANADA));
    }

    @Test
    public void test_WhenAskedForIrelandCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("ireland", sut.jsCountryValue(Country.IRELAND));
    }

    @Test
    public void test_WhenAskedForJapanCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("japan", sut.jsCountryValue(Country.JAPAN));
    }

    @Test
    public void test_WhenAskedForSwedenCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("sweden", sut.jsCountryValue(Country.SWEDEN));
    }

    @Test
    public void test_WhenAskedForUnitedArabEmiratesCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("unitedArabEmirates", sut.jsCountryValue(Country.UNITED_ARAB_EMIRATES));
    }

    @Test
    public void test_WhenAskedForUnitedKingdomCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("unitedKingdom", sut.jsCountryValue(Country.UNITED_KINGDOM));
    }

    @Test
    public void test_WhenAskedForUnitedStatesCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("unitedStates", sut.jsCountryValue(Country.UNITED_STATES));
    }

    @Test
    public void test_WhenCountryOrdinalNumberIsTooHigh_ReturnEmptyCountriesSet() {
        String[] countriesArrayValues = new String[]{"something unrecognizable"};
        ReadableArrayStub countriesArrayStub = new ReadableArrayStub(1, countriesArrayValues);
        assertTrue(sut.parseAllowedCountries(countriesArrayStub).isEmpty());
    }

    @Test
    public void test_WhenParsingNullReadableArray_ReturnEmptyCountriesSet() {
        assertTrue(sut.parseAllowedCountries(null).isEmpty());
    }

    @Test
    public void test_WhenParsingEmptyReadableArray_ReturnEmptyCountriesSet() {
        assertTrue(sut.parseAllowedCountries(new ReadableArrayStub(0, new String[]{})).isEmpty());
    }

    @Test
    public void test_WhenCountryJSValuesAreValid_ReturnCorrectCountriesSet() {
        ReadableArray countriesArrayValues = JavaOnlyArray.of(
                sut.jsCountryValue(Country.CANADA),
                sut.jsCountryValue(Country.UNITED_ARAB_EMIRATES),
                sut.jsCountryValue(Country.UNITED_KINGDOM),
                sut.jsCountryValue(Country.SWEDEN),
                sut.jsCountryValue(Country.UNITED_STATES),
                sut.jsCountryValue(Country.IRELAND),
                sut.jsCountryValue(Country.JAPAN)
        );

        Set<Country> expectedCountries = EnumSet.of(Country.CANADA, Country.UNITED_ARAB_EMIRATES, Country.UNITED_KINGDOM,
                Country.SWEDEN, Country.UNITED_STATES, Country.IRELAND, Country.JAPAN);
        Set<Country> adaptedCountries = sut.parseAllowedCountries(countriesArrayValues);

        assertEquals(countriesArrayValues.size(), adaptedCountries.size());
        assertEquals(adaptedCountries, expectedCountries);
    }

    @Test
    public void test_WhenSomeCountryJSValuesAreInvalid_TheyShouldBeIgnored() {
        ReadableArray countriesWithSomeInvalidValues = JavaOnlyArray.of(
                sut.jsCountryValue(Country.CANADA),
                "something unrecognizable",
                sut.jsCountryValue(Country.UNITED_KINGDOM),
                -1,
                sut.jsCountryValue(Country.UNITED_STATES),
                sut.jsCountryValue(Country.IRELAND),
                sut.jsCountryValue(Country.JAPAN));

        Set<Country> expectedCountries = EnumSet.of(Country.CANADA, Country.UNITED_KINGDOM,
                Country.UNITED_STATES, Country.IRELAND, Country.JAPAN);
        Set<Country> adaptedCountries = sut.parseAllowedCountries(countriesWithSomeInvalidValues);

        assertEquals(adaptedCountries, expectedCountries);
    }

    private void assertCorrectCountryValueFor(String key,
                                              Country countryValue,
                                              Map<String, String> map) {
        assertNotEquals(key, "notFound");
        assertTrue(map.containsKey(key));
        assertEquals(map.get(key), sut.jsCountryValue(countryValue));
    }
}
