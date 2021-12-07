package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyMap;
import com.fidelreactlibrary.adapters.ResultsAdapter;
import com.fidelreactlibrary.fakes.CardSchemeAdapterStub;
import com.fidelreactlibrary.fakes.CountryAdapterStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

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
}
