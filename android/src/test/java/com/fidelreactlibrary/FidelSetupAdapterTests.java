package com.fidelreactlibrary;

import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.FidelSetupKeys;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class FidelSetupAdapterTests {

    private static final String TEST_SDK_KEY = "pk_123123123";
    private static final String TEST_PROGRAM_ID = "234234";
    private static final String TEST_COMPANY_NAME = "some company name";
    
    private FidelSetupAdapter sut;
    
    @Before
    public final void setUp() {
        sut = new FidelSetupAdapter();
    }
    
    @After
    public final void tearDown() {
        sut = null;
        Fidel.sdkKey = null;
        Fidel.programId = null;
        Fidel.companyName = null;
    }

    @Test
    public void test_WhenDataHasNoKeys_DoNotSetAnyPropertyForFidel() {
        sut.process(ReadableMapStub.mapWithNoKey());
        assertNull(Fidel.sdkKey);
        assertNull(Fidel.programId);
        assertNull(Fidel.companyName);
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
}
