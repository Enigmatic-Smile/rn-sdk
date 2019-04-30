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
    }

    //Verification values tests
    @Test
    public void test_ChecksForBannerImage() {
        assertToCheckForKey(FidelOptionsAdapter.BANNER_IMAGE_KEY);
    }

    @Test
    public void test_ChecksForAutoScanValue() {
        assertToCheckForKey(FidelOptionsAdapter.AUTOSCAN_KEY);
    }

    @Test
    public void test_IfHasBannerImageKeyButNoImage_DontSendDataToImageAdapter() {
        ReadableMapStub map = new ReadableMapStub(FidelOptionsAdapter.BANNER_IMAGE_KEY, FidelOptionsAdapter.BANNER_IMAGE_KEY);
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfHasAutoScanKeyButNoValue_DontSetItToTheSDK() {
        ReadableMapStub map = new ReadableMapStub(FidelOptionsAdapter.AUTOSCAN_KEY, FidelOptionsAdapter.AUTOSCAN_KEY);
        map.boolToReturn = true;
        sut.process(map);
        assertFalse(Fidel.autoScan);
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


    //Setting correct values tests
    @Test
    public void test_WhenImageProcessorSendsBitmap_SetItForFidelBannerImage() {
        Bitmap newBitmap = Bitmap.createBitmap(100, 200, Bitmap.Config.ALPHA_8);
        sut.output(newBitmap);
        assertEquals(Fidel.bannerImage, newBitmap);
    }

    @Test
    public void test_WhenAutoScanValueIsTrue_SetItTrueForFidelSDK() {
        processWithBoolean(true);
        assertTrue(Fidel.autoScan);
    }

    @Test
    public void test_WhenAutoScanValueIsFalse_SetItFalseForFidelSDK() {
        processWithBoolean(false);
        assertFalse(Fidel.autoScan);
    }

    //Helper functions
    private void processWithBoolean(Boolean bool) {
        ReadableMapStub map = mapWithExistingKey(FidelOptionsAdapter.AUTOSCAN_KEY);
        map.boolToReturn = bool;
        sut.process(map);
    }

    private ReadableMapStub mapWithExistingKey(String existingKey) {
        return new ReadableMapStub(existingKey, "");
    }

    private void assertToCheckForKey(String keyToCheckFor) {
        ReadableMapStub map = mapWithExistingKey(keyToCheckFor);
        sut.process(map);
        assertThat(map.keyNamesCheckedFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesVerifiedNullFor, hasItem(keyToCheckFor));
        assertThat(map.keyNamesAskedFor, hasItem(keyToCheckFor));
    }
}
