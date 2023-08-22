
package com.fidelreactlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ViewManager;
import com.fidelapi.utils.FidelExperimental;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;
import com.fidelreactlibrary.adapters.FidelCardVerificationChoiceAdapter;
import com.fidelreactlibrary.adapters.FidelCountryAdapter;
import com.fidelreactlibrary.adapters.FidelProgramTypeAdapter;
import com.fidelreactlibrary.adapters.FidelSetupAdapter;
import com.fidelreactlibrary.adapters.FidelVerificationConfigurationAdapter;
import com.fidelreactlibrary.adapters.ImageFromReadableMapAdapter;
import com.fidelreactlibrary.adapters.ResultsAdapter;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.events.BridgeLibraryEvent;
import com.fidelreactlibrary.events.CardVerificationChoiceObserver;
import com.fidelreactlibrary.events.CardVerificationStartedObserver;
import com.fidelreactlibrary.events.ResultsObserver;
import com.fidelreactlibrary.events.BridgeLibraryEventEmitter;

@OptIn(markerClass = FidelExperimental.class)
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
                new FidelProgramTypeAdapter());
        imageAdapter.bitmapOutput = setupAdapter;

        List<ConstantsProvider> constantsProviderList = new ArrayList<>();

        ResultsAdapter resultsAdapter = new ResultsAdapter(WritableNativeMap::new, countryAdapter, cardSchemeAdapter);
        DataProcessor<ReadableMap> resultHandler = new BridgeLibraryEventEmitter(reactContext,
                BridgeLibraryEvent.RESULT_AVAILABLE);
        ResultsObserver resultsObserver = new ResultsObserver(resultsAdapter, resultHandler, WritableNativeMap::new);

        DataProcessor<ReadableMap> cardVerificationStartedHandler = new BridgeLibraryEventEmitter(reactContext,
                BridgeLibraryEvent.CARD_VERIFICATION_STARTED);
        DataProcessor<ReadableMap> cardVerificationChoiceHandler = new BridgeLibraryEventEmitter(reactContext,
                BridgeLibraryEvent.CARD_VERIFICATION_CHOICE);
        CardVerificationStartedObserver cardVerificationStartedObserver = new CardVerificationStartedObserver(
                cardVerificationStartedHandler, WritableNativeMap::new);
        FidelCardVerificationChoiceAdapter cardVerificationChoiceAdapter = new FidelCardVerificationChoiceAdapter(WritableNativeMap::new);
        CardVerificationChoiceObserver cardVerificationChoiceObserver = new CardVerificationChoiceObserver(
                cardVerificationChoiceHandler, cardVerificationChoiceAdapter);

        constantsProviderList.add(setupAdapter);
        constantsProviderList.add(resultsAdapter);
        constantsProviderList.add(resultsObserver);
        constantsProviderList.add(cardVerificationChoiceAdapter);

        FidelModule fidelModule = new FidelModule(
                reactContext,
                setupAdapter,
                resultsObserver,
                cardVerificationStartedObserver,
                cardVerificationChoiceObserver,
                constantsProviderList,
                new FidelVerificationConfigurationAdapter());
        return Collections.singletonList(fidelModule);
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
