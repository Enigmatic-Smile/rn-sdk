
package com.fidelreactlibrary;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ViewManager;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;
import com.fidelreactlibrary.adapters.FidelProgramTypeAdapter;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.ImageFromReadableMapAdapter;
import com.fidelreactlibrary.adapters.WritableMapDataAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.events.ResultAvailableEventEmitter;

public class FidelPackage implements ReactPackage {

    @Override
    public @NonNull List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        ImageFromReadableMapAdapter imageAdapter = new ImageFromReadableMapAdapter(reactContext);
        FidelSetupAdapter setupAdapter = new FidelSetupAdapter(
                imageAdapter,
                new FidelCountryAdapter(),
                new FidelCardSchemesAdapter(),
                new FidelProgramTypeAdapter()
        );
        imageAdapter.bitmapOutput = setupAdapter;

        List<ConstantsProvider> constantsProviderList = new ArrayList<>();
        constantsProviderList.add(setupAdapter);


        DataAdapter<Object, WritableMap> resultAdapter = new WritableMapDataAdapter(WritableNativeMap::new);
        DataProcessor<WritableMap> resultHandler = new ResultAvailableEventEmitter(reactContext);
        OnResultObserver onResultObserver = new CallbackActivityEventListener(resultAdapter, resultHandler);

        FidelModule fidelModule = new FidelModule(reactContext, setupAdapter, onResultObserver, constantsProviderList);
        return Collections.singletonList(fidelModule);
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}