package com.fidelreactlibrary.events;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelapi.entities.EnrollmentResult;
import com.fidelreactlibrary.events.ResultsObserver;
import com.fidelreactlibrary.fakes.DataAdapterStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;

import org.junit.After;
import org.junit.Before;

public class ResultsObserverTests {

    private DataAdapterStub<Object, WritableMap> resultAdapterStub = new DataAdapterStub<>();
    private DataProcessorSpy<ReadableMap> resultHandlerSpy = new DataProcessorSpy<>();
    private ResultsObserver sut = new ResultsObserver(resultAdapterStub, resultHandlerSpy, JavaOnlyMap::new);

    private static final EnrollmentResult testLinkResult = new EnrollmentResult(
            "TEST CARD ID", "", "", -1, CardScheme.MASTERCARD,
            false, 2021,12, Country.CANADA, "444400", "4001", null
    );

    @Before
    public final void setUp() {
        resultAdapterStub.convertedDataToReturn = new JavaOnlyMap();
    }

    @After
    public final void tearDown() {
        sut = null;
        resultAdapterStub = null;
        resultHandlerSpy = null;
    }
}
