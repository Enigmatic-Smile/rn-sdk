
package com.fidelreactlibrary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.adapters.ImageFromReadableMapAdapter;

public class FidelPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        ImageFromReadableMapAdapter imageAdapter = new ImageFromReadableMapAdapter(reactContext);
        FidelOptionsAdapter optionsAdapter = new FidelOptionsAdapter(imageAdapter);
        imageAdapter.bitmapOutput = optionsAdapter;
      return Arrays.<NativeModule>asList(new FidelModule(reactContext, optionsAdapter));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }
}