package com.fidelreactlibrary;

import android.content.Context;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.abstraction.OnCardVerificationChoiceSelectedObserver;
import com.fidelapi.entities.abstraction.OnCardVerificationStartedObserver;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.fakes.ConstantsProviderStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReactContextMock;
import com.fidelreactlibrary.fakes.ReadableMapStub;
import com.fidelreactlibrary.fakes.VerificationConfigurationAdapterStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapContainsMap;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FidelModuleTests {

    private FidelModule sut;
    private DataProcessorSpy<ReadableMap> setupAdapterSpy = new DataProcessorSpy<>();;
    private List<ConstantsProvider> constantsProviderListStub = new ArrayList<>();
    private final OnResultObserver testOnResultObserver = fidelResult -> {};
    private final OnCardVerificationStartedObserver testOnCardVerificationStartedObserver = consentDetails -> {};

    private final OnCardVerificationChoiceSelectedObserver testOnCardVerificationChoiceSelectedObserver = cardVerificationChoice -> {};

    @Before
    public final void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        ReactContextMock reactContext = new ReactContextMock(context);
        sut = new FidelModule(reactContext,
                setupAdapterSpy,
                testOnResultObserver,
                testOnCardVerificationStartedObserver,
                testOnCardVerificationChoiceSelectedObserver,
                constantsProviderListStub,
                new VerificationConfigurationAdapterStub()
        );
    }

    @After
    public final void tearDown() {
        sut = null;
        setupAdapterSpy = null;
        constantsProviderListStub = null;
    }

    @Test
    public void test_WhenGettingConstants_ReturnConstantsFromAllConstantsProvider() {
        ConstantsProvider constantsProvider1 = new ConstantsProviderStub("testModuleConstantKey", 345);
        ConstantsProvider constantsProvider2 = new ConstantsProviderStub("testModuleConstantKey2", 348);
        constantsProviderListStub.add(constantsProvider1);
        constantsProviderListStub.add(constantsProvider2);
        assertMapContainsMap(sut.getConstants(), constantsProvider1.getConstants());
        assertMapContainsMap(sut.getConstants(), constantsProvider2.getConstants());
    }

    @Test
    public void test_WhenAskedToSetup_ForwardSetupDataToSetupAdapter() {
        ReadableMapStub fakeMap = new ReadableMapStub();
        sut.setup(fakeMap);
        assertEquals(setupAdapterSpy.dataToProcess, fakeMap);
    }

    @Test
    public void test_WhenAResultAvailableListenerHasBeenAdded_AddTheOnResultObserverToFidel() {
        sut.addListener("ResultAvailable");
        assertEquals(testOnResultObserver, Fidel.onResult);
    }

    @Test
    public void test_WhenACardVerificationStartedListenerHasBeenAdded_AddTheObserverToFidel() {
        sut.addListener("CardVerificationStarted");
        assertEquals(testOnCardVerificationStartedObserver, Fidel.onCardVerificationStarted);
    }

    @Test
    public void test_WhenACardVerificationChoiceSelectedListenerHasBeenAdded_AddTheObserverToFidel() {
        sut.addListener("CardVerificationChoiceSelected");
        assertEquals(testOnCardVerificationChoiceSelectedObserver, Fidel.onCardVerificationChoiceSelected);
    }

    @Test
    public void test_WhenListenersHaveBeenRemoved_AddTheOnResultObserverToFidel() {
        Fidel.onResult = testOnResultObserver;
        sut.removeListeners(0);
        assertNull(Fidel.onResult);
    }
}
