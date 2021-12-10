package com.fidelreactlibrary.fakes;

import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public final class ConstantsProviderStub implements ConstantsProvider {

    private final String key;
    private final Integer value;

    public ConstantsProviderStub(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Nonnull
    @Override
    public Map<String, Object> getConstants() {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(key, value);
        return hashMap;
    }
}
