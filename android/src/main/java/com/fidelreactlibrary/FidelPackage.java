
package com.fidelreactlibrary;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ViewManager;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;
import com.fidelreactlibrary.adapters.FidelProgramTypeAdapter;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.ImageFromReadableMapAdapter;
import com.fidelreactlibrary.adapters.ResultsAdapter;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.events.BridgeLibraryEventTypes;
import com.fidelreactlibrary.events.CardVerificationStartedObserver;
import com.fidelreactlibrary.events.ResultsObserver;
import com.fidelreactlibrary.events.BridgeLibraryEventEmitter;

public class FidelPackage implements ReactPackage {

    @Override
    public @NonNull List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        ImageFromReadableMapAdapter imageAdapter = new ImageFromReadableMapAdapter(reactContext);
        CountryAdapter countryAdapter = new FidelCountryAdapter();
        CardSchemesAdapter cardSchemeAdapter = new FidelCardSchemesAdapter();
        FidelSetupAdapter setupAdapter = new FidelSetupAdapter(
                imageAdapter,
                countryAdapter,
                cardSchemeAdapter,
                new FidelProgramTypeAdapter()
        );
        imageAdapter.bitmapOutput = setupAdapter;

        List<ConstantsProvider> constantsProviderList = new ArrayList<>();

        ResultsAdapter resultsAdapter = new ResultsAdapter(WritableNativeMap::new, countryAdapter, cardSchemeAdapter);
        DataProcessor<ReadableMap> resultHandler = new BridgeLibraryEventEmitter(reactContext, BridgeLibraryEventTypes.RESULT_AVAILABLE);
        ResultsObserver resultsObserver = new ResultsObserver(resultsAdapter, resultHandler, WritableNativeMap::new);

        DataProcessor<ReadableMap> cardVerificationStartedHandler = new BridgeLibraryEventEmitter(reactContext, BridgeLibraryEventTypes.CARD_VERIFICATION_STARTED);
        CardVerificationStartedObserver cardVerificationStartedObserver = new CardVerificationStartedObserver(cardVerificationStartedHandler, WritableNativeMap::new);

        constantsProviderList.add(setupAdapter);
        constantsProviderList.add(resultsAdapter);
        constantsProviderList.add(resultsObserver);

        FidelModule fidelModule = new FidelModule(
                reactContext,
                setupAdapter,
                resultsObserver,
                cardVerificationStartedObserver,
                constantsProviderList
        );
        return Collections.singletonList(fidelModule);
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
