package com.fidelreactlibrary.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardVerificationChoice;
import com.fidelapi.entities.abstraction.OnCardVerificationChoiceSelectedObserver;
import com.fidelapi.utils.FidelExperimental;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

@FidelExperimental
public class CardVerificationChoiceObserver implements OnCardVerificationChoiceSelectedObserver {

    private final ObjectFactory<WritableMap> writableMapFactory;
    private final DataProcessor<ReadableMap> adaptedVerificationChoiceDetailsProcessor;

    public CardVerificationChoiceObserver(DataProcessor<ReadableMap> adaptedVerificationChoiceDetailsProcessor,
            ObjectFactory<WritableMap> writableMapFactory) {
        this.adaptedVerificationChoiceDetailsProcessor = adaptedVerificationChoiceDetailsProcessor;
        this.writableMapFactory = writableMapFactory;
    }

    @Override
    public void onCardVerificationChoiceSelected(@NonNull CardVerificationChoice cardVerificationChoice) {
        WritableMap map = writableMapFactory.create();
        map.putString("verificationChoice", cardVerificationChoice.name());
        adaptedVerificationChoiceDetailsProcessor.process(map);
    }
}
