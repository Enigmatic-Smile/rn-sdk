package com.fidelreactlibrary;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.FidelSetupKeys;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Test;

import static junit.framework.TestCase.*;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.graphics.Bitmap;

// Custom test runner is necessary for being able to use JSONObject and Bitmap
@RunWith(RobolectricTestRunner.class)
public class FidelSetupAdapterTests {

    private static final String TEST_SDK_KEY = "pk_123123123";
    private static final String TEST_PROGRAM_ID = "234234";
    private static final String TEST_COMPANY_NAME = "some company name";


    private final DataProcessorSpy<ReadableMap> imageAdapterSpy = new DataProcessorSpy<>();
    private FidelSetupAdapter sut = new FidelSetupAdapter(imageAdapterSpy);
    
    @After
    public final void tearDown() {
        sut = null;
        Fidel.sdkKey = null;
        Fidel.programId = null;
        Fidel.companyName = null;
        Fidel.bannerImage = null;
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
        assertTrue(map.keyNamesAskedFor.contains(FidelSetupKeys.OPTIONS.jsName()));
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
    public void test_WhenReceivingBannerImageReadableMap_SendItToImageProcessor() {
        ReadableMapStub readableMap = ReadableMapStub.mapWithAllValidSetupKeys();
        sut.process(readableMap);
        ReadableMapStub optionsMap = (ReadableMapStub)readableMap.getMap(FidelSetupKeys.OPTIONS.jsName());
        assertNotNull(optionsMap);
        assertEquals(optionsMap.mapsForKeysToReturn.get(FidelSetupKeys.Options.BANNER_IMAGE.jsName()), imageAdapterSpy.dataToProcess);
    }

    @Test
    public void test_IfHasBannerImageKeyButNoImage_ShouldSendDataToImageAdapterAnyways() {
        ReadableMapStub map = ReadableMapStub.withNullValueForOptionKey(FidelSetupKeys.Options.BANNER_IMAGE);
        sut.process(map);
        assertTrue(imageAdapterSpy.hasAskedToProcessData);
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
}
