package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyMap;
import com.fidelapi.entities.FidelError;
import com.fidelapi.entities.FidelErrorType;
import com.fidelreactlibrary.adapters.ResultsAdapter;
import com.fidelreactlibrary.fakes.CardSchemeAdapterStub;
import com.fidelreactlibrary.fakes.CountryAdapterStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import java.util.Map;

//Custom test runner is necessary for being able to use JSONObject
@RunWith(RobolectricTestRunner.class)
public class ResultsAdapterTests {

    private final CountryAdapterStub countryAdapterStub = new CountryAdapterStub();
    private final CardSchemeAdapterStub cardSchemeAdapterStub = new CardSchemeAdapterStub();
    private ResultsAdapter sut;

    @Before
    public final void setUp() {
        sut = new ResultsAdapter(JavaOnlyMap::new, countryAdapterStub, cardSchemeAdapterStub);
    }

    @After
    public final void tearDown() {
        sut = null;
    }

    @Test
    public void test_WhenAskedToConvertNullObject_ReturnNull() {
        assertNull(sut.getAdaptedObjectFor(null));
    }

    @Test
    public void test_WhenAskedToConvertDeviceNotSecureObject_ShouldReturnCorrectErrorTypeForJS() {
        FidelError fidelError = new FidelError(FidelErrorType.DeviceNotSecure.INSTANCE, "some message", 123);
        assertEquals("deviceNotSecure", sut.getAdaptedObjectFor(fidelError).getString("type"));
    }

    @Test
    public void test_WhenAskedToConvertDeviceNotSecureObject_ShouldReturnCorrectErrorMessageForJS() {
        String testMessage = "some message";
        FidelError fidelError = new FidelError(FidelErrorType.DeviceNotSecure.INSTANCE, testMessage, 123);
        assertEquals(testMessage, sut.getAdaptedObjectFor(fidelError).getString("message"));
    }

    @Test
    public void test_WhenAskedToConvertDeviceNotSecureObject_ShouldReturnCorrectDateForJS() {
        long testDate = 123;
        FidelError fidelError = new FidelError(FidelErrorType.DeviceNotSecure.INSTANCE, "some message", testDate);
        assertEquals(testDate, sut.getAdaptedObjectFor(fidelError).getDouble("date"), 0.0001);
    }

    @Test
    public void test_WhenAskedToGetAllConstantsToExport_ShouldIncludeDeviceNotSecureError() {
        Map<String, Object> constants = sut.getConstants();
        Object errorTypeConstants = constants.get("ErrorType");
        if (!(errorTypeConstants instanceof Map)) {
            fail("You should get a map of error types here");
            return;
        }
        Map<String, String> errorTypeConstantsMap = (Map<String, String>)errorTypeConstants;
        assertEquals("deviceNotSecure", errorTypeConstantsMap.get("deviceNotSecure"));
    }
}
