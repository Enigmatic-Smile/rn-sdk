package com.fidelreactlibrary.events;

import android.content.Context;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.fidelreactlibrary.fakes.ReactContextMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class BridgeLibraryEventEmitterTests {

    private BridgeLibraryEventEmitter sut;
    private ReactContextMock reactContext;

    @Before
    public final void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        reactContext = new ReactContextMock(context);
    }

    @After
    public final void tearDown() {
        sut = null;
    }

    @Test
    public void test_WhenReceivingWritableData_AskForJSModule() {
        sut = new BridgeLibraryEventEmitter(reactContext, BridgeLibraryEvent.RESULT_AVAILABLE);
        WritableMap map = new JavaOnlyMap();
        map.putString("testKey", "testValue");
        sut.process(map);
        assertEquals(reactContext.classAskedFor, DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    @Test
    public void test_WhenReceivingWritableData_SendCorrectResultAvailableEventUsingTheObtainedJSModule() {
        sut = new BridgeLibraryEventEmitter(reactContext, BridgeLibraryEvent.RESULT_AVAILABLE);
        WritableMap map = new JavaOnlyMap();
        map.putString("testKey", "testValue");
        sut.process(map);
        assertEquals(reactContext.receivedErrorName, BridgeLibraryEvent.RESULT_AVAILABLE.getEventName());
        assertEquals(reactContext.receivedErrorData, map);
        assertEquals(reactContext.eventEmitterInvokedMethodName, "emit");
    }
}
