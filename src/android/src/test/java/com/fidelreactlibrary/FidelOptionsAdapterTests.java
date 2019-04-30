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

    private static final String TEST_COMPANY_NAME = "Test Company Name Inc.";

    @Before
    public final void setUp() {
        imageAdapterSpy = new DataProcessorSpy();
        sut = new FidelOptionsAdapter(imageAdapterSpy);
    }

    @After
    public final void tearDown() {
        imageAdapterSpy = null;
        sut = null;
        Fidel.bannerImage = null;
        Fidel.autoScan = false;
        Fidel.companyName = null;
    }

    //Verification values tests
    @Test
    public void test_ChecksAllKeys() {
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.BANNER_IMAGE_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.AUTO_SCAN_KEY));
        assertThat(FidelOptionsAdapter.OPTION_KEYS, hasItem(FidelOptionsAdapter.COMPANY_NAME_KEY));
        for (String key: FidelOptionsAdapter.OPTION_KEYS) {
            assertToCheckForKey(key);
        }
    }

    @Test
    public void test_IfHasBannerImageKeyButNoImage_DontSendDataToImageAdapter() {
        ReadableMapStub map = mapWithExistingKeyButNoValue(FidelOptionsAdapter.BANNER_IMAGE_KEY);
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfHasAutoScanKeyButNoValue_DontSetItToTheSDK() {
        ReadableMapStub map = mapWithExistingKeyButNoValue(FidelOptionsAdapter.AUTO_SCAN_KEY);
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_IfHasCompanyNameKeyButNoValue_DontSetItToTheSDK() {
        ReadableMapStub map = mapWithExistingKeyButNoValue(FidelOptionsAdapter.COMPANY_NAME_KEY);
        map.stringToReturn = TEST_COMPANY_NAME;
        sut.process(map);
        assertNotEquals(Fidel.companyName, map.stringToReturn);
    }

    @Test
    public void test_IfDoesntHaveBannerImageKey_DontSendDataToImageAdapter() {
        ReadableMapStub map = new ReadableMapStub("", "");
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfDoesntHaveAutoScanKey_DontSetItToTheSDK() {
        ReadableMapStub map = new ReadableMapStub("", "");
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
    }

    @Test
    public void test_IfDoesntHaveCompanyNameKey_DontSetItToTheSDK() {
        ReadableMapStub map = new ReadableMapStub("", "");
        map.stringToReturn = TEST_COMPANY_NAME;
        sut.process(map);
        assertNotEquals(Fidel.companyName, map.stringToReturn);
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
        ReadableMapStub map = mapWithExistingKey(FidelOptionsAdapter.COMPANY_NAME_KEY);
        map.stringToReturn = TEST_COMPANY_NAME;
        sut.process(map);
        assertEquals(Fidel.companyName, map.stringToReturn);
    }

    //Helper functions
    private void processWithBoolean(Boolean bool) {
        ReadableMapStub map = mapWithExistingKey(FidelOptionsAdapter.AUTO_SCAN_KEY);
        map.boolToReturn = bool;
        sut.process(map);
    }

    private ReadableMapStub mapWithExistingKey(String existingKey) {
        return new ReadableMapStub(existingKey, "");
    }

    private ReadableMapStub mapWithExistingKeyButNoValue(String existingKey) {
        return new ReadableMapStub(existingKey, existingKey);
    }

    private void assertToCheckForKey(String keyToCheckFor) {
        ReadableMapStub map = mapWithExistingKey(keyToCheckFor);
        sut.process(map);
        assertThat(map.keyNamesCheckedFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesVerifiedNullFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesAskedFor, hasItem(keyToCheckFor));
    }
}
