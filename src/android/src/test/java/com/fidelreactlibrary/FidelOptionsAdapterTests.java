package com.fidelreactlibrary;

import android.graphics.Bitmap;

import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class FidelOptionsAdapterTests {

    private DataProcessorSpy imageAdapterSpy;
    private FidelOptionsAdapter sut;
    private ReadableMapStub map;

    private static final String TEST_COMPANY_NAME = "Test Company Name Inc.";
    private static final String TEST_DELETE_INSTRUCTIONS = "Test Delete instructions.";

    @Before
    public final void setUp() {
        imageAdapterSpy = new DataProcessorSpy();
        sut = new FidelOptionsAdapter(imageAdapterSpy);
    }

    @After
    public final void tearDown() {
        imageAdapterSpy = null;
        sut = null;
        map = null;
        Fidel.bannerImage = null;
        Fidel.autoScan = false;
        Fidel.companyName = null;
        Fidel.deleteInstructions = null;
    }

    //Verification values tests
    @Test
    public void test_ChecksAllKeys() {
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.BANNER_IMAGE_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.AUTO_SCAN_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.COMPANY_NAME_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY));
        for (String key: FidelOptionsAdapter.OPTION_KEYS) {
            assertToCheckForKey(key);
        }
    }

    @Test
    public void test_IfHasBannerImageKeyButNoImage_DontSendDataToImageAdapter() {
        map = mapWithExistingKeyButNoValue(FidelOptionsAdapter.BANNER_IMAGE_KEY);
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfHasAutoScanKeyButNoValue_DontSetItToTheSDK() {
        map = mapWithExistingKeyButNoValue(FidelOptionsAdapter.AUTO_SCAN_KEY);
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_IfHasCompanyNameKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.COMPANY_NAME_KEY;
        map = mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_COMPANY_NAME, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.companyName);
    }

    @Test
    public void test_IfHasDeleteInstructionsKeyButNoValue_DontSetItToTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        map = mapWithExistingKeyButNoValue(keyToTestFor);
        processWithString(TEST_DELETE_INSTRUCTIONS, keyToTestFor);
        assertNotEqualsString(keyToTestFor, Fidel.deleteInstructions);
    }

    @Test
    public void test_IfDoesntHaveBannerImageKey_DontSendDataToImageAdapter() {
        map = mapWithNoKey();
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfDoesntHaveAutoScanKey_DontSetItToTheSDK() {
        map = mapWithNoKey();
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_IfDoesntHaveCompanyNameKey_DontSetItToTheSDK() {
        map = mapWithNoKey();
        String key = FidelOptionsAdapter.COMPANY_NAME_KEY;
        processWithString(TEST_COMPANY_NAME, key);
        assertNotEqualsString(key, Fidel.companyName);
    }

    @Test
    public void test_IfDoesntHaveDeleteInstructionsKey_DontSetThemToTheSDK() {
        map = mapWithNoKey();
        String key = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        processWithString(TEST_DELETE_INSTRUCTIONS, key);
        assertNotEqualsString(key, Fidel.deleteInstructions);
    }

    //Setting correct values tests
    @Test
    public void test_WhenImageProcessorSendsBitmap_SendItToImageProcessor() {
        ReadableMapStub map = mapWithExistingKey(FidelOptionsAdapter.BANNER_IMAGE_KEY);
        map.mapToReturn = new ReadableMapStub();
        sut.process(map);
        assertEquals(map.mapToReturn, imageAdapterSpy.dataToProcess);
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
        map = mapWithExistingKey(keyToTestFor);
        processWithString(TEST_COMPANY_NAME, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.companyName);
    }

    @Test
    public void test_WhenDeleteInstructionsValueIsSet_SetItForTheSDK() {
        String keyToTestFor = FidelOptionsAdapter.DELETE_INSTRUCTIONS_KEY;
        map = mapWithExistingKey(keyToTestFor);
        processWithString(TEST_DELETE_INSTRUCTIONS, keyToTestFor);
        assertEqualsString(keyToTestFor, Fidel.deleteInstructions);
    }

    //Helper functions
    private void processWithBoolean(Boolean bool) {
        map = mapWithExistingKey(FidelOptionsAdapter.AUTO_SCAN_KEY);
        map.boolToReturn = bool;
        sut.process(map);
    }
    private void processWithString(String string, String key) {
        map.stringForKeyToReturn.put(key, string);
        sut.process(map);
    }

    private ReadableMapStub mapWithNoKey() {
        return new ReadableMapStub("", "");
    }

    private ReadableMapStub mapWithExistingKey(String existingKey) {
        return new ReadableMapStub(existingKey, "");
    }

    private ReadableMapStub mapWithExistingKeyButNoValue(String existingKey) {
        return new ReadableMapStub(existingKey, existingKey);
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
        ReadableMapStub map = mapWithExistingKey(keyToCheckFor);
        sut.process(map);
        assertThat(map.keyNamesCheckedFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesVerifiedNullFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesAskedFor, hasItem(keyToCheckFor));
    }
}
