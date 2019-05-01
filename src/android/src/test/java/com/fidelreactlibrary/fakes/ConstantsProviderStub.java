package com.fidelreactlibrary.fakes;

import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;

import java.util.HashMap;
import java.util.Map;

public final class ConstantsProviderStub implements ConstantsProvider {
    @Override
    public Map<String, Object> getConstants() {
        final HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("testConstant", 1);
        return hashMap;
    }
}
