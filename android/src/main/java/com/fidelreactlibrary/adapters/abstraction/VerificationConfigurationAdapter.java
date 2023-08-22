package com.fidelreactlibrary.adapters.abstraction;

import com.fidelapi.entities.CardVerificationConfiguration;
import com.facebook.react.bridge.ReadableMap;

public interface VerificationConfigurationAdapter {

    CardVerificationConfiguration adapt(ReadableMap data);
}
