package com.fidelreactlibrary.adapters.abstraction;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.entities.CardVerificationChoice;

public interface CardVerificationChoiceAdapter {

    ReadableMap adapt(@NonNull CardVerificationChoice cardVerificationChoice);
}
