package com.fidelreactlibrary.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardVerificationChoice;
import com.fidelapi.entities.abstraction.OnCardVerificationChoiceSelectedObserver;
import com.fidelapi.utils.FidelExperimental;
import com.fidelreactlibrary.adapters.abstraction.CardVerificationChoiceAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

@FidelExperimental
public class CardVerificationChoiceObserver implements OnCardVerificationChoiceSelectedObserver {
    private final DataProcessor<ReadableMap> adaptedVerificationChoiceDetailsProcessor;
    private final CardVerificationChoiceAdapter cardVerificationChoiceAdapter;

    public CardVerificationChoiceObserver(DataProcessor<ReadableMap> adaptedVerificationChoiceDetailsProcessor,
                                          CardVerificationChoiceAdapter cardVerificationChoiceAdapter) {
        this.adaptedVerificationChoiceDetailsProcessor = adaptedVerificationChoiceDetailsProcessor;
        this.cardVerificationChoiceAdapter = cardVerificationChoiceAdapter;
    }

    @Override
    public void onCardVerificationChoiceSelected(@NonNull CardVerificationChoice cardVerificationChoice) {
        adaptedVerificationChoiceDetailsProcessor.process(cardVerificationChoiceAdapter.adapt(cardVerificationChoice));
    }
}
