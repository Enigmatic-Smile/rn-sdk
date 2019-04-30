package com.fidelreactlibrary;

import android.content.Context;

import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReactContextMock;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class FidelModuleTests {

    @Test
    public void test_WhenSettingOptions_ForwardThemToOptionsAdapter() {
        Context context = ApplicationProvider.getApplicationContext();
        ReactContextMock reactContext = new ReactContextMock(context);
        DataProcessorSpy optionsAdapterSpy = new DataProcessorSpy();
        FidelModule sut = new FidelModule(reactContext, optionsAdapterSpy);
        ReadableMapStub fakeMap = new ReadableMapStub();
        sut.setOptions(fakeMap);
        assertEquals(optionsAdapterSpy.dataToProcess, fakeMap);
    }
}
