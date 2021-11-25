package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelapi.entities.EnrollmentResult;
import com.fidelapi.entities.FidelError;
import com.fidelapi.entities.FidelErrorType;
import com.fidelapi.entities.FidelResult;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.fakes.CallbackSpy;
import com.fidelreactlibrary.fakes.DataConverterStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallbackActivityEventListenerTests {

    private CallbackActivityEventListener sut;
    private CallbackSpy callbackSpy;
    private DataConverterStub<Object, WritableMap> linkResultConverterStub;
    private DataProcessorSpy<WritableMap> errorHandlerSpy;

    private static final FidelError testLinkResultError = new FidelError(FidelErrorType.UserCanceled.INSTANCE, "test error message", -1);
    private static final EnrollmentResult testLinkResult = new EnrollmentResult(
            "TEST CARD ID", "", "", -1, CardScheme.MASTERCARD,
            false, 2021,12, Country.CANADA, "444400", "4001", null
    );

    @Before
    public final void setUp() {
        linkResultConverterStub = new DataConverterStub<>();
        errorHandlerSpy = new DataProcessorSpy<>();
        sut = new CallbackActivityEventListener(linkResultConverterStub, errorHandlerSpy);
        callbackSpy = new CallbackSpy();
        sut.callbackIsReady(callbackSpy);

        linkResultConverterStub.convertedDataToReturn = new JavaOnlyMap();
    }

    @After
    public final void tearDown() {
        sut = null;
        callbackSpy = null;
        linkResultConverterStub = null;
        errorHandlerSpy = null;
    }

    @Test
    public void test_WhenReceivingLinkResult_SendItConvertedInCallback() {
        sut.onResultAvailable(new FidelResult.Enrollment(testLinkResult));

        assertTrue(callbackSpy.didInvoke);
        assertEquals(linkResultConverterStub.convertedDataToReturn, callbackSpy.receivedResultMap);
    }
}
