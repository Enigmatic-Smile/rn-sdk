package com.fidelreactlibrary;

import android.content.Context;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.fakes.ConstantsProviderStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReactContextMock;
import com.fidelreactlibrary.fakes.ReadableMapStub;

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
    private final OnResultObserver testOnResultObserver = fidelResult -> { };
    
    @Before
    public final void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        ReactContextMock reactContext = new ReactContextMock(context);
        ConstantsProvider constantsProvider = new ConstantsProviderStub("testModuleConstantKey", 345);
        constantsProviderListStub.add(constantsProvider);
        sut = new FidelModule(reactContext,
                setupAdapterSpy,
                testOnResultObserver,
                constantsProviderListStub);
    }
    
    @After
    public final void tearDown() {
        sut = null;
        setupAdapterSpy = null;
        constantsProviderListStub = null;
    }

    @Test
    public void test_WhenGettingConstants_ReturnConstantsFromFirstConstantsProvider() {
        assertMapContainsMap(sut.getConstants(), constantsProviderListStub.get(0).getConstants());
    }

    @Test
    public void test_WhenAskedToSetup_ForwardSetupDataToSetupAdapter() {
        ReadableMapStub fakeMap = new ReadableMapStub();
        sut.setup(fakeMap);
        assertEquals(setupAdapterSpy.dataToProcess, fakeMap);
    }

    @Test
    public void test_WhenAListenerHasBeenAdded_AddTheOnResultObserverToFidel() {
        sut.addListener("any event");
        assertEquals(testOnResultObserver, Fidel.onResult);
    }

    @Test
    public void test_WhenListenersHaveBeenRemoved_AddTheOnResultObserverToFidel() {
        Fidel.onResult = testOnResultObserver;
        sut.removeListeners(0);
        assertNull(Fidel.onResult);
    }
}
