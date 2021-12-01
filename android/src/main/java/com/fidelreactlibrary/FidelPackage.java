
package com.fidelreactlibrary;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.ImageFromReadableMapAdapter;
import com.fidelreactlibrary.adapters.WritableMapDataConverter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.events.ErrorEventEmitter;

public class FidelPackage implements ReactPackage {

    @Override
    public @NonNull List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        ImageFromReadableMapAdapter imageAdapter =
                new ImageFromReadableMapAdapter(reactContext);
        CountryAdapter countryAdapter =
                new FidelCountryAdapter();

        FidelCardSchemesAdapter cardSchemesAdapter =
                new FidelCardSchemesAdapter();

        FidelSetupAdapter setupAdapter =
                new FidelSetupAdapter(imageAdapter, countryAdapter);

        imageAdapter.bitmapOutput = setupAdapter;

        FidelOptionsAdapter optionsAdapter =
                new FidelOptionsAdapter(cardSchemesAdapter);
        List<ConstantsProvider> constantsProviderList =
                new ArrayList<>();
        constantsProviderList.add(setupAdapter);
        WritableMapDataConverter linkResultConverter =
                new WritableMapDataConverter(new ObjectFactory<WritableMap>() {
                    @Override
                    public WritableMap create() {
                        return new WritableNativeMap();
                    }
                });
        ErrorEventEmitter errorEventEmitter =
                new ErrorEventEmitter(reactContext);
        CallbackActivityEventListener activityEventListener =
                new CallbackActivityEventListener(linkResultConverter, errorEventEmitter);
      return Arrays.<NativeModule>asList(
              new FidelModule(reactContext,
                      setupAdapter,
                      optionsAdapter,
                      constantsProviderList,
                      activityEventListener));
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