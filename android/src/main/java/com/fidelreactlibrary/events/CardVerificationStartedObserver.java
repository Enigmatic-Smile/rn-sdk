package com.fidelreactlibrary.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.ConsentDetails;
import com.fidelapi.entities.abstraction.OnCardVerificationStartedObserver;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

public class CardVerificationStartedObserver implements OnCardVerificationStartedObserver {

    private final DataProcessor<ReadableMap> adaptedConsentDetailsProcessor;
    private final ObjectFactory<WritableMap> writableMapFactory;

    public CardVerificationStartedObserver(DataProcessor<ReadableMap> adaptedConsentDetailsProcessor,
                                           ObjectFactory<WritableMap> writableMapFactory) {
        this.adaptedConsentDetailsProcessor = adaptedConsentDetailsProcessor;
        this.writableMapFactory = writableMapFactory;
    }

    @Override
    public void onCardVerificationStarted(@NonNull ConsentDetails consentDetails) {
        WritableMap map = writableMapFactory.create();
        map.putString("cardId", consentDetails.getCardId());
        map.putString("consentId", consentDetails.getConsentId());
        map.putString("programId", consentDetails.getProgramId());
        adaptedConsentDetailsProcessor.process(map);
    }
}
