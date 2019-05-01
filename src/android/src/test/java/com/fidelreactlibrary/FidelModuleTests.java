package com.fidelreactlibrary;

import android.content.Context;

import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.fakes.ConstantsProviderStub;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReactContextMock;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;

import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapContainsMap;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class FidelModuleTests {
    
    private FidelModule sut;
    private DataProcessorSpy optionsAdapterSpy;
    private DataProcessorSpy setupAdapterSpy;
    private List<ConstantsProvider> constantsProviderListStub;
    
    @Before
    public final void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        ReactContextMock reactContext = new ReactContextMock(context);
        optionsAdapterSpy = new DataProcessorSpy();
        setupAdapterSpy = new DataProcessorSpy();
        constantsProviderListStub = new ArrayList<>();
        ConstantsProvider constantsProvider = new ConstantsProviderStub();
        constantsProviderListStub.add(constantsProvider);
        sut = new FidelModule(reactContext, setupAdapterSpy, optionsAdapterSpy, constantsProviderListStub);
    }
    
    @After
    public final void tearDown() {
        sut = null;
        optionsAdapterSpy = null;
        constantsProviderListStub = null;
    }

    @Test
    public void test_WhenSettingOptions_ForwardThemToOptionsAdapter() {
        ReadableMapStub fakeMap = new ReadableMapStub();
        sut.setOptions(fakeMap);
        assertEquals(optionsAdapterSpy.dataToProcess, fakeMap);
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
}
