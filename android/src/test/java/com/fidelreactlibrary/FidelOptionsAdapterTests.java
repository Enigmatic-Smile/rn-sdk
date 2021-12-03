package com.fidelreactlibrary;

import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;


// Custom test runner is necessary for being able to use JSONObject
@RunWith(RobolectricTestRunner.class)
public class FidelOptionsAdapterTests {

    private ReadableMapStub map;
    private FidelOptionsAdapter sut = new FidelOptionsAdapter();

    private static final String TEST_PROGRAM_NAME = "Test Program Name";
    private static final String TEST_DELETE_INSTRUCTIONS = "Test Delete instructions.";
    private static final String TEST_PRIVACY_URL = "testprivacy.url";
    private static final String TEST_TERMS_CONDITIONS_URL = "termsconditions.url";

    @After
    public final void tearDown() {
        sut = null;
        Fidel.shouldAutoScanCard = false;
        Fidel.programName = null;
        Fidel.deleteInstructions = null;
        Fidel.privacyPolicyUrl = null;
        Fidel.termsAndConditionsUrl = null;
        Fidel.metaData = null;
    }

    //Tests when keys are present, but no data is found for that key

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

    //Setting correct values tests

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

    //Helper functions

    private void processWithString(String string, String key) {
        map.stringForKeyToReturn.put(key, string);
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
}
