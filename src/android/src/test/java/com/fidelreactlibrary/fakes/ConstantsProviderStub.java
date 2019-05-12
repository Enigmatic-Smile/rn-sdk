package com.fidelreactlibrary.fakes;

import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;

import java.util.HashMap;
import java.util.Map;

public final class ConstantsProviderStub implements ConstantsProvider {

    private String key;
    private Integer value;

    public ConstantsProviderStub(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Map<String, Object> getConstants() {
        final HashMap<String, Object> hashMap = new HashMap();
        hashMap.put(key, value);
        return hashMap;
    }
}
