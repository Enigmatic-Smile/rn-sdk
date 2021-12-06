package com.fidelreactlibrary;

import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapContainsMap;
import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapEqualsWithJSONObject;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.FidelSetupKeys;
import com.fidelreactlibrary.fakes.CardSchemeAdapterStub;
import com.fidelreactlibrary.fakes.CountryAdapterStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReadableArrayStub;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.graphics.Bitmap;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Custom test runner is necessary for being able to use JSONObject and Bitmap
@RunWith(RobolectricTestRunner.class)
public class FidelSetupAdapterTests {

    private static final String TEST_SDK_KEY = "pk_123123123";
    private static final String TEST_PROGRAM_ID = "234234";
    private static final String TEST_COMPANY_NAME = "some company name";

    private static final Set<Country> TEST_COUNTRIES = EnumSet.of(Country.UNITED_KINGDOM, Country.JAPAN, Country.CANADA);
    private static final Set<CardScheme> TEST_CARD_SCHEMES_SET = EnumSet.of(CardScheme.VISA, CardScheme.AMERICAN_EXPRESS);


    private final DataProcessorSpy<ReadableMap> imageAdapterSpy = new DataProcessorSpy<>();
    private final CountryAdapterStub countryAdapterStub = new CountryAdapterStub();
    private final CardSchemeAdapterStub cardSchemesAdapterStub = new CardSchemeAdapterStub();
    private FidelSetupAdapter sut = new FidelSetupAdapter(imageAdapterSpy, countryAdapterStub, cardSchemesAdapterStub);
    
    @After
    public final void tearDown() {
        sut = null;
        Fidel.sdkKey = null;
        Fidel.programId = null;
        Fidel.companyName = null;
        Fidel.bannerImage = null;
        Fidel.allowedCountries = EnumSet.allOf(Country.class);
        Fidel.supportedCardSchemes = EnumSet.allOf(CardScheme.class);
        Fidel.shouldAutoScanCard = false;
        Fidel.metaData = null;
    }

    @Test
    public void test_WhenDataHasNoKeys_DoNotSetAnyPropertyForFidel() {
        sut.process(ReadableMapStub.mapWithNoKey());
        assertNull(Fidel.sdkKey);
        assertNull(Fidel.programId);
        assertNull(Fidel.companyName);
        assertNull(Fidel.bannerImage);
    }

    @Test
    public void test_WhenDataHasNoSdkKey_DoNotSetThisPropertyForFidel() {
        sut.process(ReadableMapStub.withoutKey(FidelSetupKeys.SDK_KEY));
        assertNull(Fidel.sdkKey);
    }

    @Test
    public void test_WhenDataHasNoProgramIDKey_DoNotSetThisPropertyForFidel() {
        sut.process(ReadableMapStub.withoutKey(FidelSetupKeys.PROGRAM_ID));
        assertNull(Fidel.programId);
    }

    @Test
    public void test_WhenDataHasNoCompanyNameKey_DoNotSetThisPropertyForFidel() {
        sut.process(ReadableMapStub.withoutKey(FidelSetupKeys.COMPANY_NAME));
        assertNull(Fidel.companyName);
    }

    @Test
    public void test_WhenDataHasNoValueForSdkKey_DoNotSetThisPropertyForFidel() {
        sut.process(ReadableMapStub.withNullValueForKey(FidelSetupKeys.SDK_KEY));
        assertNull(Fidel.sdkKey);
    }

    @Test
    public void test_WhenDataHasNoValueForProgramIDKey_DoNotSetThisPropertyForFidel() {
        sut.process(ReadableMapStub.withNullValueForKey(FidelSetupKeys.PROGRAM_ID));
        assertNull(Fidel.programId);
    }

    @Test
    public void test_WhenDataHasNoValueForCompanyNameKey_DoNotSetThisPropertyForFidel() {
        sut.process(ReadableMapStub.withNullValueForKey(FidelSetupKeys.COMPANY_NAME));
        assertNull(Fidel.companyName);
    }

    @Test
    public void test_WhenDataHasEmptyValueForSdkKey_ShouldSetEmptyValueForThisPropertyForFidel() {
        sut.process(ReadableMapStub.withEmptyValueForKey(FidelSetupKeys.SDK_KEY));
        assertNotNull(Fidel.sdkKey);
        assertTrue(Fidel.sdkKey.isEmpty());
    }

    @Test
    public void test_WhenDataHasEmptyValueForProgramId_ShouldSetEmptyValueForThisPropertyForFidel() {
        sut.process(ReadableMapStub.withEmptyValueForKey(FidelSetupKeys.PROGRAM_ID));
        assertNotNull(Fidel.programId);
        assertTrue(Fidel.programId.isEmpty());
    }

    @Test
    public void test_WhenDataHasEmptyValueForCompanyName_ShouldSetEmptyValueForThisPropertyForFidel() {
        sut.process(ReadableMapStub.withEmptyValueForKey(FidelSetupKeys.COMPANY_NAME));
        assertNotNull(Fidel.companyName);
        assertTrue(Fidel.companyName.isEmpty());
    }

    @Test
    public void test_WhenApiKeyIsSet_SetItToSDK() {
        ReadableMapStub readableMap = ReadableMapStub.mapWithAllValidSetupKeys();
        readableMap.putString(FidelSetupKeys.SDK_KEY.jsName(), TEST_SDK_KEY);
        sut.process(readableMap);
        assertEquals(TEST_SDK_KEY, Fidel.sdkKey);
    }

    @Test
    public void test_WhenProgramIDIsSet_SetItToSDK() {
        ReadableMapStub readableMap = ReadableMapStub.mapWithAllValidSetupKeys();
        readableMap.putString(FidelSetupKeys.PROGRAM_ID.jsName(), TEST_PROGRAM_ID);
        sut.process(readableMap);
        assertEquals(TEST_PROGRAM_ID, Fidel.programId);
    }

    @Test
    public void test_WhenCompanyNameIsSet_SetItToSDK() {
        ReadableMapStub readableMap = ReadableMapStub.mapWithAllValidSetupKeys();
        readableMap.putString(FidelSetupKeys.COMPANY_NAME.jsName(), TEST_COMPANY_NAME);
        sut.process(readableMap);
        assertEquals(TEST_COMPANY_NAME, Fidel.companyName);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotSetBannerImageForFidel() {
        sut.process(ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS));
        assertNull(Fidel.bannerImage);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotTryToProcessBannerImageInformationWithTheImageAdapter() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfDoesNotHaveBannerImageKey_DoNotSetBannerImageForFidel() {
        sut.process(ReadableMapStub.withoutOptionsKey(FidelSetupKeys.Options.BANNER_IMAGE));
        assertNull(Fidel.bannerImage);
    }

    @Test
    public void test_IfDoesNotHaveBannerImageKey_ShouldSendDataToImageAdapterAnyways() {
        sut.process(ReadableMapStub.withoutOptionsKey(FidelSetupKeys.Options.BANNER_IMAGE));
        assertTrue(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfHasBannerImageKeyButNoImage_ShouldSendDataToImageAdapterAnyways() {
        ReadableMapStub map = ReadableMapStub.withNullValueForOptionKey(FidelSetupKeys.Options.BANNER_IMAGE);
        sut.process(map);
        assertTrue(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_WhenReceivingBannerImageReadableMap_SendItToImageProcessor() {
        ReadableMapStub readableMap = ReadableMapStub.mapWithAllValidSetupKeys();
        sut.process(readableMap);
        ReadableMapStub optionsMap = (ReadableMapStub)readableMap.getMap(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        assertEquals(optionsMap.mapsForKeysToReturn.get(FidelSetupKeys.Options.BANNER_IMAGE.jsName()), imageAdapterSpy.dataToProcess);
    }

    @Test
    public void test_WhenImageProcessorSendsBitmap_SetItForSDKBannerImage() {
        Bitmap newBitmap = Bitmap.createBitmap(100, 200, Bitmap.Config.ALPHA_8);
        sut.output(newBitmap);
        assertEquals(Fidel.bannerImage, newBitmap);
    }

    @Test
    public void test_WhenImageProcessorSendsNull_SetNullSDKBannerImage() {
        Fidel.bannerImage = Bitmap.createBitmap(100, 200, Bitmap.Config.ALPHA_8);
        sut.output(null);
        assertNull(Fidel.bannerImage);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotSetAllowedCountriesForFidel() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        Fidel.allowedCountries = TEST_COUNTRIES;
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertEquals(TEST_COUNTRIES, Fidel.allowedCountries);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotTryToProcessCountriesWithTheCountryAdapter() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertFalse(countryAdapterStub.askedToParseAllowedCountries);
    }

    @Test
    public void test_IfDoesNotHaveAllowedCountriesKey_DoNotSetAllowedCountriesForFidel() {
        Fidel.allowedCountries = TEST_COUNTRIES;
        countryAdapterStub.countriesToReturn = EnumSet.allOf(Country.class);
        sut.process(ReadableMapStub.withoutOptionsKey(FidelSetupKeys.Options.ALLOWED_COUNTRIES));
        assertEquals(TEST_COUNTRIES, Fidel.allowedCountries);
    }

    @Test
    public void test_IfDoesHaveAllowedCountriesKey_ButNullValue_ShouldSetAdaptedCountriesForFidel() {
        Fidel.allowedCountries = TEST_COUNTRIES;
        countryAdapterStub.countriesToReturn = EnumSet.allOf(Country.class);
        ReadableMapStub map = ReadableMapStub.withNullValueForOptionKey(FidelSetupKeys.Options.ALLOWED_COUNTRIES);
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);

        sut.process(map);

        assertTrue(optionsMap.keyNamesAskedValueFor.contains(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName()));
        assertEquals(EnumSet.allOf(Country.class), Fidel.allowedCountries);
    }

    @Test
    public void test_WhenAllowedCountriesAreSet_ConvertThemWithCountryAdapterForTheSDK() {
        countryAdapterStub.countriesToReturn = TEST_COUNTRIES;
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);

        sut.process(map);

        assertTrue(optionsMap.keyNamesAskedValueFor.contains(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName()));
        assertEquals(TEST_COUNTRIES, Fidel.allowedCountries);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotSetSupportedCardSchemesForFidel() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        Fidel.supportedCardSchemes = TEST_CARD_SCHEMES_SET;
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertEquals(TEST_CARD_SCHEMES_SET, Fidel.supportedCardSchemes);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotTryToProcessCardSchemesWithTheCardSchemesAdapter() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertFalse(cardSchemesAdapterStub.askedToAdaptCardSchemes);
    }

    @Test
    public void test_IfDoesNotHaveSupportedCardSchemesKey_DoNotSetSupportedCardSchemesForFidel() {
        Fidel.supportedCardSchemes = TEST_CARD_SCHEMES_SET;
        cardSchemesAdapterStub.fakeAdaptedCardSchemesToReturn = EnumSet.allOf(CardScheme.class);
        sut.process(ReadableMapStub.withoutOptionsKey(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES));
        assertEquals(TEST_CARD_SCHEMES_SET, Fidel.supportedCardSchemes);
    }

    @Test
    public void test_IfDoesHaveSupportedCardSchemesKey_ButNullValue_ShouldSetAdaptedCardSchemesForFidel() {
        Fidel.supportedCardSchemes = TEST_CARD_SCHEMES_SET;
        cardSchemesAdapterStub.fakeAdaptedCardSchemesToReturn = EnumSet.allOf(CardScheme.class);
        ReadableMapStub map = ReadableMapStub.withNullValueForOptionKey(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES);
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);

        sut.process(map);

        assertTrue(optionsMap.keyNamesAskedValueFor.contains(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName()));
        assertEquals(EnumSet.allOf(CardScheme.class), Fidel.supportedCardSchemes);
    }

    @Test
    public void test_WhenCardSchemesAreSet_ConvertThemWithCardSchemesAdapterForTheSDK() {
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        ReadableArrayStub readableCardSchemesArray = new ReadableArrayStub(1, new int[]{1});
        optionsMap.readableArraysToReturn.put(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName(), readableCardSchemesArray);
        cardSchemesAdapterStub.fakeAdaptedCardSchemesToReturn = EnumSet.noneOf(CardScheme.class);

        sut.process(map);

        assertEquals(readableCardSchemesArray, cardSchemesAdapterStub.cardSchemesReceived);
    }

    @Test
    public void test_WhenCardSchemesAreSet_SetThemForTheSDK() {
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        ReadableArrayStub readableCardSchemesArray = new ReadableArrayStub(1, new int[]{1});
        optionsMap.readableArraysToReturn.put(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName(), readableCardSchemesArray);

        cardSchemesAdapterStub.fakeAdaptedCardSchemesToReturn = TEST_CARD_SCHEMES_SET;

        sut.process(map);

        assertEquals(TEST_CARD_SCHEMES_SET, Fidel.supportedCardSchemes);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotSetShouldAutoScanCardPropertyForFidel() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        Fidel.shouldAutoScanCard = true;
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertTrue(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_IfDoesNotHaveShouldAutoScanCardKey_DoNotSetThisPropertyForFidel() {
        ReadableMapStub map = ReadableMapStub.withoutOptionsKey(FidelSetupKeys.Options.SHOULD_AUTO_SCAN);
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);

        Fidel.shouldAutoScanCard = true;
        sut.process(map);

        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertTrue(optionsMap.keyNamesCheckedFor.contains(FidelSetupKeys.Options.SHOULD_AUTO_SCAN.jsName()));
        assertFalse(optionsMap.keyNamesAskedValueFor.contains(FidelSetupKeys.Options.SHOULD_AUTO_SCAN.jsName()));
        assertTrue(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_WhenShouldAutoScanCardPropertyIsFalse_ShouldBeSetToFalseForTheSDK() {
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        optionsMap.boolToReturn = false;

        sut.process(map);

        assertFalse(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_WhenShouldAutoScanCardPropertyIsTrue_ShouldBeSetToTrueForTheSDK() {
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        optionsMap.boolToReturn = true;

        sut.process(map);

        assertTrue(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_WhenDataHasNoOptionsKey_DoNotSetMetadataPropertyForFidel() throws JSONException {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.OPTIONS);
        JSONObject testMetaData = new JSONObject("{\"some_code\":123 }");
        Fidel.metaData = testMetaData;
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertEquals(Fidel.metaData, testMetaData);
    }

    @Test
    public void test_IfDoesNotHaveMetadataKey_DoNotSetThisPropertyForFidel() throws JSONException {
        ReadableMapStub map = ReadableMapStub.withoutOptionsKey(FidelSetupKeys.Options.META_DATA);
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);

        JSONObject testMetaData = new JSONObject("{\"some_code\":123 }");
        Fidel.metaData = testMetaData;
        sut.process(map);

        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertTrue(optionsMap.keyNamesCheckedFor.contains(FidelSetupKeys.Options.META_DATA.jsName()));
        assertFalse(optionsMap.keyNamesAskedValueFor.contains(FidelSetupKeys.Options.META_DATA.jsName()));
        assertEquals(Fidel.metaData, testMetaData);
    }

    @Test
    public void test_IfDoesHaveMetadataKey_ButWithNullValue_ShouldNullMetaDataValueForFidel() throws JSONException {
        ReadableMapStub map = ReadableMapStub.withNullValueForOptionKey(FidelSetupKeys.Options.META_DATA);
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);

        Fidel.metaData = new JSONObject("{\"some_code\":123 }");
        sut.process(map);

        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.OPTIONS.jsName()));
        assertTrue(optionsMap.keyNamesCheckedFor.contains(FidelSetupKeys.Options.META_DATA.jsName()));
        assertTrue(optionsMap.keyNamesAskedValueFor.contains(FidelSetupKeys.Options.META_DATA.jsName()));
        assertNull(Fidel.metaData);
    }

    @Test
    public void test_WhenMetaDataValueIsSet_ConvertItToJSONForTheSDK() {
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        optionsMap.mapsForKeysToReturn.put(FidelSetupKeys.Options.META_DATA.jsName(), TEST_META_DATA());

        sut.process(map);

        assertNotNull(Fidel.metaData);
        assertMapEqualsWithJSONObject(TEST_HASH_MAP(), Fidel.metaData);
    }

    @Test
    public void test_WhenDataHasNoConsentTextKey_DoNotSetTermsAndConditionsUrlForFidel() {
        ReadableMapStub map = ReadableMapStub.withoutKey(FidelSetupKeys.CONSENT_TEXT);
        String fakeTermsAndConditionsUrl = "some test, fake terms and conditions";
        Fidel.termsAndConditionsUrl = fakeTermsAndConditionsUrl;
        sut.process(map);
        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.CONSENT_TEXT.jsName()));
        assertEquals(fakeTermsAndConditionsUrl, Fidel.termsAndConditionsUrl);
    }

    @Test
    public void test_IfDoesNotHaveTermsAndConditionsUrlKey_DoNotSetThisPropertyForFidel() {
        ReadableMapStub map = ReadableMapStub.withoutConsentTextKey(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL);
        ReadableMapStub consentTextMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.CONSENT_TEXT.jsName());
        assertNotNull(consentTextMap);

        String fakeTermsAndConditionsUrl = "some test, fake terms and conditions";
        Fidel.termsAndConditionsUrl = fakeTermsAndConditionsUrl;
        sut.process(map);

        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.CONSENT_TEXT.jsName()));
        assertTrue(consentTextMap.keyNamesCheckedFor.contains(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName()));
        assertFalse(consentTextMap.keyNamesAskedValueFor.contains(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName()));
        assertEquals(fakeTermsAndConditionsUrl, Fidel.termsAndConditionsUrl);
    }

    @Test
    public void test_IfDoesHaveTermsAndConditionsUrlKey_ButWithNullValue_ShouldSetNullTermsAndConditionsUrlForFidel() {
        ReadableMapStub map = ReadableMapStub.withNullValueForConsentTextKey(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL);
        ReadableMapStub consentTextMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.CONSENT_TEXT.jsName());
        assertNotNull(consentTextMap);

        Fidel.termsAndConditionsUrl = "some test, fake terms and conditions";
        sut.process(map);

        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.CONSENT_TEXT.jsName()));
        assertTrue(consentTextMap.keyNamesCheckedFor.contains(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName()));
        assertTrue(consentTextMap.keyNamesAskedValueFor.contains(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName()));
        assertNull(Fidel.termsAndConditionsUrl);
    }

    @Test
    public void test_IfDoesHaveTermsAndConditionsUrlKeyAndValue_ShouldSetTheValueForFidel() {
        ReadableMapStub map = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub consentTextMap = (ReadableMapStub)map.mapsForKeysToReturn.get(FidelSetupKeys.CONSENT_TEXT.jsName());
        assertNotNull(consentTextMap);
        String expectedTermsAndConditions = "some terms and conditions url";
        consentTextMap.stringForKeyToReturn.put(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName(), expectedTermsAndConditions);

        Fidel.termsAndConditionsUrl = "previous fake terms and conditions url";
        sut.process(map);

        assertTrue(map.keyNamesAskedValueFor.contains(FidelSetupKeys.CONSENT_TEXT.jsName()));
        assertTrue(consentTextMap.keyNamesCheckedFor.contains(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName()));
        assertTrue(consentTextMap.keyNamesAskedValueFor.contains(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName()));
        assertEquals(expectedTermsAndConditions, Fidel.termsAndConditionsUrl);
    }


    //Exposed constants tests

    @Test
    public void test_WhenAskedForConstants_IncludeConstantsFromCardSchemesAdapter() {
        Map<String, Object> actualConstants = sut.getConstants();
        Map<String, Object> expectedConstants = cardSchemesAdapterStub.getConstants();
        assertMapContainsMap(actualConstants, expectedConstants);
    }

    @Test
    public void test_WhenAskedForConstants_IncludeConstantsFromCountriesAdapter() {
        Map<String, Object> actualConstants = sut.getConstants();
        Map<String, Object> expectedConstants = countryAdapterStub.getConstants();
        assertMapContainsMap(actualConstants, expectedConstants);
    }

    // Helper functions

    private static HashMap<String, Object> TEST_HASH_MAP() {
        HashMap<String, Object> hashmapToReturn = new HashMap<>();
        hashmapToReturn.put("stringKey", "StringVal");
        hashmapToReturn.put("intKey", 3);
        hashmapToReturn.put("doubleKey", 4.5);
        return hashmapToReturn;
    }

    private static ReadableMapStub TEST_META_DATA() {
        ReadableMapStub map = new ReadableMapStub();
        map.hashMapToReturn = TEST_HASH_MAP();
        return map;
    }
}
