package com.fidelreactlibrary;

import android.graphics.Bitmap;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.fakes.CardSchemeAdapterStub;
import com.fidelreactlibrary.fakes.CountryAdapterStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapContainsMap;
import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapEqualsWithJSONObject;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;


//Custom test runner is necessary for being able to use JSONObject
@RunWith(RobolectricTestRunner.class)
public class FidelOptionsAdapterTests {

    private DataProcessorSpy<ReadableMap> imageAdapterSpy = new DataProcessorSpy<>();
    private ReadableMapStub map;
    private CountryAdapterStub countryAdapterStub = new CountryAdapterStub();
    private CardSchemeAdapterStub cardSchemesAdapterStub = new CardSchemeAdapterStub();
    private FidelOptionsAdapter sut = new FidelOptionsAdapter(imageAdapterSpy, countryAdapterStub, cardSchemesAdapterStub);

    private static final String TEST_COMPANY_NAME = "Test Company Name Inc.";
    private static final String TEST_PROGRAM_NAME = "Test Program Name";
    private static final String TEST_DELETE_INSTRUCTIONS = "Test Delete instructions.";
    private static final String TEST_PRIVACY_URL = "testprivacy.url";
    private static final String TEST_TERMS_CONDITIONS_URL = "termsconditions.url";
    private static final Fidel.Country TEST_COUNTRY = Fidel.Country.SWEDEN;
    private static final Integer TEST_COUNTRY_NUMBER = 12;

    @After
    public final void tearDown() {
        sut = null;
        Fidel.bannerImage = null;
        Fidel.autoScan = false;
        Fidel.companyName = null;
        Fidel.programName = null;
        Fidel.deleteInstructions = null;
        Fidel.privacyURL = null;
        Fidel.termsConditionsURL = null;
        Fidel.metaData = null;
        Fidel.country = null;
        Fidel.supportedCardSchemes = EnumSet.allOf(Fidel.CardScheme.class);
    }

    //Verification values tests
    @Test
    public void test_ChecksAllKeys() {
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.BANNER_IMAGE_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.AUTO_SCAN_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.COMPANY_NAME_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.PROGRAM_NAME_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.PRIVACY_URL_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.META_DATA_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.COUNTRY_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.CARD_SCHEMES_KEY));
        for (String key: FidelOptionsAdapter.OPTION_KEYS) {
            //for the card schemes value we only check if it exists
            if (!key.equals(FidelOptionsAdapter.CARD_SCHEMES_KEY)) {
                assertToCheckForKey(key);
            }
        }
    }

    public void test_ChecksForSupportedCardSchemes() {
        ReadableMapStub map = ReadableMapStub.mapWithExistingKey(FidelOptionsAdapter.CARD_SCHEMES_KEY);
        sut.process(map);
        assertThat(map.keyNamesCheckedFor, hasItem(FidelOptionsAdapter.CARD_SCHEMES_KEY));
    }

    //Tests when keys are present, but no data is found for that key
    @Test
    public void test_IfHasBannerImageKeyButNoImage_DontSendDataToImageAdapter() {
        map = ReadableMapStub.mapWithExistingKeyButNoValue(FidelOptionsAdapter.BANNER_IMAGE_KEY);
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfHasAutoScanKeyButNoValue_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithExistingKeyButNoValue(FidelOptionsAdapter.AUTO_SCAN_KEY);
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_IfHasCompanyNameKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.COMPANY_NAME_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_COMPANY_NAME, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.companyName);
    }

    @Test
    public void test_IfHasProgramNameKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.PROGRAM_NAME_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_PROGRAM_NAME, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.programName);
    }

    @Test
    public void test_IfHasDeleteInstructionsKeyButNoValue_DontSetThemToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_DELETE_INSTRUCTIONS, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.deleteInstructions);
    }

    @Test
    public void test_IfHasPrivacyURLKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.PRIVACY_URL_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_PRIVACY_URL, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.privacyURL);
    }

    @Test
    public void test_IfHasTermsConditionsURLKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_TERMS_CONDITIONS_URL, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.termsConditionsURL);
    }

    @Test
    public void test_IfHasMetaDataKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.META_DATA_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithMap(keyToTestFor, TEST_META_DATA());
        assertNull(Fidel.metaData);
    }

    @Test
    public void test_IfHasCountryKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.COUNTRY_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithCountryInt();
        assertNull(Fidel.country);
    }

    //Tests when keys are missing
    @Test
    public void test_IfDoesntHaveBannerImageKey_DontSendDataToImageAdapter() {
        map = ReadableMapStub.mapWithNoKey();
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfDoesntHaveAutoScanKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_IfDoesntHaveCompanyNameKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.COMPANY_NAME_KEY;
        processWithString(TEST_COMPANY_NAME, key);
        assertNotEqualsString(key, Fidel.companyName);
    }

    @Test
    public void test_IfDoesntHaveProgramNameKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.PROGRAM_NAME_KEY;
        processWithString(TEST_PROGRAM_NAME, key);
        assertNotEqualsString(key, Fidel.programName);
    }

    @Test
    public void test_IfDoesntHaveDeleteInstructionsKey_DontSetThemToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        processWithString(TEST_DELETE_INSTRUCTIONS, key);
        assertNotEqualsString(key, Fidel.deleteInstructions);
    }

    @Test
    public void test_IfDoesntHavePrivacyURLKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.PRIVACY_URL_KEY;
        processWithString(TEST_PRIVACY_URL, key);
        assertNotEqualsString(key, Fidel.privacyURL);
    }

    @Test
    public void test_IfDoesntHaveTermsConditionsURLKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY;
        processWithString(TEST_TERMS_CONDITIONS_URL, key);
        assertNotEqualsString(key, Fidel.termsConditionsURL);
    }

    @Test
    public void test_IfDoesntHaveMetaDataKey_DontSetItToTheSDK() {
        String key = FidelOptionsAdapter.META_DATA_KEY;
        map = ReadableMapStub.mapWithNoKey();
        processWithMap(key, TEST_META_DATA());
        assertNull(Fidel.metaData);
    }

    @Test
    public void test_IfDoesntHaveCountryKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        processWithCountryInt();
        assertNull(Fidel.country);
    }
    @Test
    public void test_IfDoesntHaveCardSchemeKey_DontSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        processWithCardSchemes(Fidel.CardScheme.VISA);
        assertEquals(EnumSet.allOf(Fidel.CardScheme.class), Fidel.supportedCardSchemes);
    }

    //Setting correct values tests
    @Test
    public void test_WhenImageProcessorSendsBitmap_SendItToImageProcessor() {
        String keyToTestFor = FidelOptionsAdapter.BANNER_IMAGE_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithMap(keyToTestFor, new ReadableMapStub());
        assertEquals(map.mapsForKeysToReturn.get(keyToTestFor),
                imageAdapterSpy.dataToProcess);
    }

    @Test
    public void test_WhenImageProcessorSendsBitmap_SetItForSDKBannerImage() {
        Bitmap newBitmap = Bitmap.createBitmap(100, 200, Bitmap.Config.ALPHA_8);
        sut.output(newBitmap);
        assertEquals(Fidel.bannerImage, newBitmap);
    }

    @Test
    public void test_WhenAutoScanValueIsTrue_SetItTrueForTheSDK() {
        processWithBoolean(true);
        assertTrue(Fidel.autoScan);
    }

    @Test
    public void test_WhenAutoScanValueIsFalse_SetItFalseForTheSDK() {
        processWithBoolean(false);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_WhenCompanyNameValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.COMPANY_NAME_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_COMPANY_NAME, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.companyName);
    }

    @Test
    public void test_WhenProgramNameValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.PROGRAM_NAME_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_PROGRAM_NAME, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.programName);
    }

    @Test
    public void test_WhenDeleteInstructionsValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_DELETE_INSTRUCTIONS, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.deleteInstructions);
    }

    @Test
    public void test_WhenPrivacyURLValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.PRIVACY_URL_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_PRIVACY_URL, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.privacyURL);
    }

    @Test
    public void test_WhenTermsConditionsURLValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_TERMS_CONDITIONS_URL, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.termsConditionsURL);
    }

    @Test
    public void test_WhenMetaDataValueIsSet_ConvertItToJSONForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.META_DATA_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithMap(keyToTestFor, TEST_META_DATA());
        assertMapEqualsWithJSONObject(TEST_HASH_MAP(), Fidel.metaData);
    }

    @Test
    public void test_WhenCountryIsSet_ConvertItWithCountryAdapterForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.COUNTRY_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithCountryInt();
        assertEquals(map.intToReturn, countryAdapterStub.countryIntegerReceived);
        assertEquals(countryAdapterStub.countryToReturn, Fidel.country);
    }

    @Test
    public void test_WhenCardSchemesAreSet_ConvertThemWithCountryAdapterForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.CARD_SCHEMES_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithCardSchemes(Fidel.CardScheme.VISA, Fidel.CardScheme.MASTERCARD);
        assertEquals(map.readableArrayToReturn, cardSchemesAdapterStub.cardSchemesReceived);
    }

    @Test
    public void test_WhenCardSchemesAreSet_SetThemForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.CARD_SCHEMES_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        Fidel.CardScheme[] expectedSchemes = {Fidel.CardScheme.VISA, Fidel.CardScheme.MASTERCARD};
        Set<Fidel.CardScheme> expectedSchemesSet = EnumSet.copyOf(Arrays.asList(expectedSchemes));
        processWithCardSchemes(expectedSchemes);
        assertEquals(expectedSchemesSet, Fidel.supportedCardSchemes);
    }

    //Exposed constants tests
    @Test
    public void test_WhenAskedForConstants_IncludeConstantsFromCountriesAdapter() {
        Map<String, Object> actualConstants = sut.getConstants();
        Map<String, Object> expectedConstants = countryAdapterStub.getConstants();
        assertMapContainsMap(actualConstants, expectedConstants);
    }

    @Test
    public void test_WhenAskedForConstants_IncludeConstantsFromCardSchemesAdapter() {
        Map<String, Object> actualConstants = sut.getConstants();
        Map<String, Object> expectedConstants = cardSchemesAdapterStub.getConstants();
        assertMapContainsMap(actualConstants, expectedConstants);
    }

    //Helper functions
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

    private void processWithBoolean(Boolean bool) {
        map = ReadableMapStub.mapWithExistingKey(FidelOptionsAdapter.AUTO_SCAN_KEY);
        map.boolToReturn = bool;
        sut.process(map);
    }
    private void processWithString(String string, String key) {
        map.stringForKeyToReturn.put(key, string);
        sut.process(map);
    }
    private void processWithMap(String key, ReadableMap mapToReturn) {
        map.mapsForKeysToReturn.put(key, mapToReturn);
        sut.process(map);
    }
    private void processWithCountryInt() {
        countryAdapterStub.countryToReturn = TEST_COUNTRY;
        map.intToReturn = TEST_COUNTRY_NUMBER;
        sut.process(map);
    }
    private void processWithCardSchemes(Fidel.CardScheme... cardSchemes) {
        cardSchemesAdapterStub.fakeAdaptedCardSchemesToReturn = EnumSet.copyOf(Arrays.asList(cardSchemes));
        map.readableArrayToReturn = JavaOnlyArray.of((Object[]) cardSchemes);
        sut.process(map);
    }

    private void assertEqualsString(String key, String valueToCheckWith) {
        sut.process(map);
        assertEquals(map.stringForKeyToReturn.get(key), valueToCheckWith);
    }

    private void assertNotEqualsString(String key, String valueToCheckWith) {
        sut.process(map);
        assertNotEquals(map.stringForKeyToReturn.get(key), valueToCheckWith);
    }

    private void assertToCheckForKey(String keyToCheckFor) {
        ReadableMapStub map = ReadableMapStub.mapWithExistingKey(keyToCheckFor);
        sut.process(map);
        assertThat(map.keyNamesCheckedFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesVerifiedNullFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesAskedFor, hasItem(keyToCheckFor));
    }
}
