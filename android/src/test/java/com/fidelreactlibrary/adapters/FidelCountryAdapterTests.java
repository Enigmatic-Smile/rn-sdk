package com.fidelreactlibrary.adapters;

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

   @Test public void test_WhenAskedForCanadaCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("canada", sut.jsCountryValue(Country.CANADA));
    }

    @Test public void test_WhenAskedForIrelandCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("ireland", sut.jsCountryValue(Country.IRELAND));
    }

    @Test public void test_WhenAskedForJapanCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("japan", sut.jsCountryValue(Country.JAPAN));
    }

    @Test public void test_WhenAskedForSwedenCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("sweden", sut.jsCountryValue(Country.SWEDEN));
    }

    @Test public void test_WhenAskedForUnitedArabEmiratesCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("unitedArabEmirates", sut.jsCountryValue(Country.UNITED_ARAB_EMIRATES));
    }

    @Test public void test_WhenAskedForUnitedKingdomCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("unitedKingdom", sut.jsCountryValue(Country.UNITED_KINGDOM));
    }

    @Test public void test_WhenAskedForUnitedStatesCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("unitedStates", sut.jsCountryValue(Country.UNITED_STATES));
    }

    @Test public void test_WhenAskedForNorwayCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("norway", sut.jsCountryValue(Country.NORWAY));
    }

    @Test public void test_WhenAskedForAustraliaCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("australia", sut.jsCountryValue(Country.AUSTRALIA));
    }

    @Test public void test_WhenAskedForNewZealandCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("newZealand", sut.jsCountryValue(Country.NEW_ZEALAND));
    }

    @Test public void test_WhenAskedForHongKongCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("hongKong", sut.jsCountryValue(Country.HONG_KONG));
    }

    @Test public void test_WhenAskedForPhilippinesCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("philippines", sut.jsCountryValue(Country.PHILIPPINES));
    }

    @Test public void test_WhenAskedForSingaporeCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("singapore", sut.jsCountryValue(Country.SINGAPORE));
    }

    @Test public void test_WhenAskedForVietnamCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("vietnam", sut.jsCountryValue(Country.VIETNAM));
    }

    @Test public void test_WhenAskedForSwitzerlandCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("switzerland", sut.jsCountryValue(Country.SWITZERLAND));
    }

    @Test public void test_WhenAskedForFinlandCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("finland", sut.jsCountryValue(Country.FINLAND));
    }

    @Test public void test_WhenAskedForDenmarkCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("denmark", sut.jsCountryValue(Country.DENMARK));
    }

    @Test public void test_WhenAskedForBrazilCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("brazil", sut.jsCountryValue(Country.BRAZIL));
    }

    @Test public void test_WhenAskedForEgyptCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("egypt", sut.jsCountryValue(Country.EGYPT));
    }

    @Test public void test_WhenAskedForOmanCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("oman", sut.jsCountryValue(Country.OMAN));
    }

    @Test public void test_WhenAskedForQatarCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("qatar", sut.jsCountryValue(Country.QATAR));
    }

    @Test public void test_WhenAskedForBahrainCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("bahrain", sut.jsCountryValue(Country.BAHRAIN));
    }

    @Test public void test_WhenAskedForKuwaitCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("kuwait", sut.jsCountryValue(Country.KUWAIT));
    }

    @Test public void test_WhenAskedForSaudiArabiaCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("saudiArabia", sut.jsCountryValue(Country.SAUDI_ARABIA));
    }

    @Test public void test_WhenAskedForJordanCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("jordan", sut.jsCountryValue(Country.JORDAN));
    }

    @Test public void test_WhenAskedForSouthAfricaCountryValue_ItProvidesTheCorrectOne() {
        assertEquals("southAfrica", sut.jsCountryValue(Country.SOUTH_AFRICA));
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
            sut.jsCountryValue(Country.JAPAN),
            sut.jsCountryValue(Country.NORWAY),
            sut.jsCountryValue(Country.AUSTRALIA),
            sut.jsCountryValue(Country.NEW_ZEALAND),
            sut.jsCountryValue(Country.HONG_KONG),
            sut.jsCountryValue(Country.PHILIPPINES),
            sut.jsCountryValue(Country.SINGAPORE),
            sut.jsCountryValue(Country.VIETNAM),
            sut.jsCountryValue(Country.SWITZERLAND),
            sut.jsCountryValue(Country.FINLAND),
            sut.jsCountryValue(Country.DENMARK),
            sut.jsCountryValue(Country.BRAZIL),
            sut.jsCountryValue(Country.EGYPT),
            sut.jsCountryValue(Country.OMAN),
            sut.jsCountryValue(Country.QATAR),
            sut.jsCountryValue(Country.BAHRAIN),
            sut.jsCountryValue(Country.KUWAIT),
            sut.jsCountryValue(Country.SAUDI_ARABIA),
            sut.jsCountryValue(Country.JORDAN),
            sut.jsCountryValue(Country.SOUTH_AFRICA)
        );

        Set<Country> expectedCountries = EnumSet.of(
            Country.CANADA,
            Country.UNITED_ARAB_EMIRATES,
            Country.UNITED_KINGDOM,
            Country.SWEDEN,
            Country.UNITED_STATES,
            Country.IRELAND,
            Country.JAPAN,
            Country.NORWAY,
            Country.AUSTRALIA,
            Country.NEW_ZEALAND,
            Country.HONG_KONG,
            Country.PHILIPPINES,
            Country.SINGAPORE,
            Country.VIETNAM,
            Country.SWITZERLAND,
            Country.FINLAND,
            Country.DENMARK,
            Country.BRAZIL,
            Country.EGYPT,
            Country.OMAN,
            Country.QATAR,
            Country.BAHRAIN,
            Country.KUWAIT,
            Country.SAUDI_ARABIA,
            Country.JORDAN,
            Country.SOUTH_AFRICA
        );

        Set<Country> adaptedCountries = sut.parseAllowedCountries(countriesArrayValues);

        assertEquals(countriesArrayValues.size(), adaptedCountries.size());
        assertEquals(expectedCountries, adaptedCountries);
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
