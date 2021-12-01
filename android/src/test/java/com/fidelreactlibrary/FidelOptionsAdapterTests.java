package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.fakes.CardSchemeAdapterStub;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapEqualsWithJSONObject;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;


// Custom test runner is necessary for being able to use JSONObject
@RunWith(RobolectricTestRunner.class)
public class FidelOptionsAdapterTests {

    private ReadableMapStub map;
    private CardSchemeAdapterStub cardSchemesAdapterStub = new CardSchemeAdapterStub();
    private FidelOptionsAdapter sut = new FidelOptionsAdapter(cardSchemesAdapterStub);

    private static final String TEST_PROGRAM_NAME = "Test Program Name";
    private static final String TEST_DELETE_INSTRUCTIONS = "Test Delete instructions.";
    private static final String TEST_PRIVACY_URL = "testprivacy.url";
    private static final String TEST_TERMS_CONDITIONS_URL = "termsconditions.url";

    @After
    public final void tearDown() {
        sut = null;
        Fidel.shouldAutoScanCard = false;
        Fidel.companyName = null;
        Fidel.programName = null;
        Fidel.deleteInstructions = null;
        Fidel.privacyPolicyUrl = null;
        Fidel.termsAndConditionsUrl = null;
        Fidel.metaData = null;
        Fidel.allowedCountries = EnumSet.allOf(Country.class);
        Fidel.supportedCardSchemes = EnumSet.allOf(CardScheme.class);
    }

    //Verification values tests
    @Test
    public void test_ChecksAllKeys() {
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.SHOULD_AUTO_SCAN_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.PROGRAM_NAME_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.PRIVACY_POLICY_URL_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.META_DATA_KEY));
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
    public void test_IfHasAutoScanKeyButNoValue_DoNotSetItToTheSDK() {
        map = ReadableMapStub.mapWithExistingKeyButNoValue(FidelOptionsAdapter.SHOULD_AUTO_SCAN_KEY);
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_IfHasProgramNameKeyButNoValue_DoNotSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.PROGRAM_NAME_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_PROGRAM_NAME, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.programName);
    }

    @Test
    public void test_IfHasDeleteInstructionsKeyButNoValue_DoNotSetThemToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_DELETE_INSTRUCTIONS, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.deleteInstructions);
    }

    @Test
    public void test_IfHasPrivacyURLKeyButNoValue_DoNotSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.PRIVACY_POLICY_URL_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_PRIVACY_URL, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.privacyPolicyUrl);
    }

    @Test
    public void test_IfHasTermsConditionsURLKeyButNoValue_DoNotSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_TERMS_CONDITIONS_URL, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.termsAndConditionsUrl);
    }

    @Test
    public void test_IfHasMetaDataKeyButNoValue_DoNotSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.META_DATA_KEY;
        map = ReadableMapStub.mapWithExistingKeyButNoValue(keyToTestFor);
        processWithMap(keyToTestFor, TEST_META_DATA());
        assertNull(Fidel.metaData);
    }

    //Tests when keys are missing

    @Test
    public void test_IfDoesNotHaveAutoScanKey_DoNotSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_IfDoesNotHaveProgramNameKey_DoNotSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.PROGRAM_NAME_KEY;
        processWithString(TEST_PROGRAM_NAME, key);
        assertNotEqualsString(key, Fidel.programName);
    }

    @Test
    public void test_IfDoesNotHaveDeleteInstructionsKey_DoNotSetThemToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        processWithString(TEST_DELETE_INSTRUCTIONS, key);
        assertNotEqualsString(key, Fidel.deleteInstructions);
    }

    @Test
    public void test_IfDoesNotHavePrivacyURLKey_DoNotSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.PRIVACY_POLICY_URL_KEY;
        processWithString(TEST_PRIVACY_URL, key);
        assertNotEqualsString(key, Fidel.privacyPolicyUrl);
    }

    @Test
    public void test_IfDoesNotHaveTermsConditionsURLKey_DoNotSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        String key = FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY;
        processWithString(TEST_TERMS_CONDITIONS_URL, key);
        assertNotEqualsString(key, Fidel.termsAndConditionsUrl);
    }

    @Test
    public void test_IfDoesNotHaveMetaDataKey_DoNotSetItToTheSDK() {
        String key = FidelOptionsAdapter.META_DATA_KEY;
        map = ReadableMapStub.mapWithNoKey();
        processWithMap(key, TEST_META_DATA());
        assertNull(Fidel.metaData);
    }

    @Test
    public void test_IfDoesNotHaveCardSchemeKey_DoNotSetItToTheSDK() {
        map = ReadableMapStub.mapWithNoKey();
        processWithCardSchemes(CardScheme.VISA);
        assertEquals(EnumSet.allOf(CardScheme.class), Fidel.supportedCardSchemes);
    }

    //Setting correct values tests

    @Test
    public void test_WhenAutoScanValueIsTrue_SetItTrueForTheSDK() {
        processWithBoolean(true);
        assertTrue(Fidel.shouldAutoScanCard);
    }

    @Test
    public void test_WhenAutoScanValueIsFalse_SetItFalseForTheSDK() {
        processWithBoolean(false);
        assertFalse(Fidel.shouldAutoScanCard);
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
        String keyToTestFor = FidelOptionsAdapter.PRIVACY_POLICY_URL_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_PRIVACY_URL, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.privacyPolicyUrl);
    }

    @Test
    public void test_WhenTermsConditionsURLValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.TERMS_CONDITIONS_URL_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithString(TEST_TERMS_CONDITIONS_URL, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.termsAndConditionsUrl);
    }

    @Test
    public void test_WhenMetaDataValueIsSet_ConvertItToJSONForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.META_DATA_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithMap(keyToTestFor, TEST_META_DATA());
        assertMapEqualsWithJSONObject(TEST_HASH_MAP(), Fidel.metaData);
    }

    @Test
    public void test_WhenCardSchemesAreSet_ConvertThemWithCountryAdapterForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.CARD_SCHEMES_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        processWithCardSchemes(CardScheme.VISA, CardScheme.MASTERCARD);
        assertEquals(map.readableArraysToReturn.get(keyToTestFor), cardSchemesAdapterStub.cardSchemesReceived);
    }

    @Test
    public void test_WhenCardSchemesAreSet_SetThemForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.CARD_SCHEMES_KEY;
        map = ReadableMapStub.mapWithExistingKey(keyToTestFor);
        CardScheme[] expectedSchemes = {CardScheme.VISA, CardScheme.MASTERCARD};
        Set<CardScheme> expectedSchemesSet = EnumSet.copyOf(Arrays.asList(expectedSchemes));
        processWithCardSchemes(expectedSchemes);
        assertEquals(expectedSchemesSet, Fidel.supportedCardSchemes);
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
        map = ReadableMapStub.mapWithExistingKey(FidelOptionsAdapter.SHOULD_AUTO_SCAN_KEY);
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
    private void processWithCardSchemes(CardScheme... cardSchemes) {
        cardSchemesAdapterStub.fakeAdaptedCardSchemesToReturn = EnumSet.copyOf(Arrays.asList(cardSchemes));
        map.readableArraysToReturn.put(FidelOptionsAdapter.CARD_SCHEMES_KEY, JavaOnlyArray.of((Object[]) cardSchemes));
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
