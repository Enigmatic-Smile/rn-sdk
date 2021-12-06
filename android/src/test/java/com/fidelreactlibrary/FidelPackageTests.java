package com.fidelreactlibrary;

import android.content.Context;

import com.facebook.react.bridge.NativeModule;
import com.fidelreactlibrary.fakes.ReactContextMock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class FidelPackageTests {
    @Test
    public void test_WhenCreatingNativeModules_ItShouldProvideFidelModule() {
        FidelPackage fidelPackage = new FidelPackage();
        Context context = ApplicationProvider.getApplicationContext();
        ReactContextMock reactContext = new ReactContextMock(context);
        List<NativeModule> modules = fidelPackage.createNativeModules(reactContext);
        assertEquals(1, modules.size());
        FidelModule fidelModule = (FidelModule)modules.get(0);
        assertNotNull(fidelModule);
    }

    @Test
    public void test_WhenCreatingNativeModules_FidelModuleShouldReturnConstants() {
        FidelPackage fidelPackage = new FidelPackage();
        Context context = ApplicationProvider.getApplicationContext();
        ReactContextMock reactContext = new ReactContextMock(context);
        List<NativeModule> modules = fidelPackage.createNativeModules(reactContext);
        FidelModule fidelModule = (FidelModule)modules.get(0);
        assertNotNull(fidelModule.getConstants());
    }
}
