package com.fidelreactlibrary.fakes;

import com.fidelreactlibrary.adapters.CountryAdapter;

import java.util.Map;

public final class CountryAdapterStub implements CountryAdapter {
    @Override
    public Map<String, Object> getConstants() {
        ConstantsProviderStub constantsProviderStub = new ConstantsProviderStub();
        return constantsProviderStub.getConstants();
    }
}
