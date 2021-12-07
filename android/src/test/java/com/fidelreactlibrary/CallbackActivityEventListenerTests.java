package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelapi.entities.EnrollmentResult;
import com.fidelapi.entities.FidelResult;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.fakes.DataAdapterStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallbackActivityEventListenerTests {

    private CallbackActivityEventListener sut;
    private DataAdapterStub<Object, WritableMap> linkResultConverterStub;
    private DataProcessorSpy<WritableMap> errorHandlerSpy;

    private static final EnrollmentResult testLinkResult = new EnrollmentResult(
            "TEST CARD ID", "", "", -1, CardScheme.MASTERCARD,
            false, 2021,12, Country.CANADA, "444400", "4001", null
    );

    @Before
    public final void setUp() {
        linkResultConverterStub = new DataAdapterStub<>();
        errorHandlerSpy = new DataProcessorSpy<>();
        sut = new CallbackActivityEventListener(linkResultConverterStub, errorHandlerSpy);

        linkResultConverterStub.convertedDataToReturn = new JavaOnlyMap();
    }

    @After
    public final void tearDown() {
        sut = null;
        linkResultConverterStub = null;
        errorHandlerSpy = null;
    }
}
