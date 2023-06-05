package com.fidelreactlibrary.adapters.abstraction;

import com.fidelapi.entities.CardVerificationConfiguration;
import com.facebook.react.bridge.ReadableMap;

public interface VerificationSetupAdapter {

    CardVerificationConfiguration map(ReadableMap data);
}
