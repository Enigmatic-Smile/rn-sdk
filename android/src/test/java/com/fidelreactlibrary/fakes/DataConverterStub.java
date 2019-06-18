package com.fidelreactlibrary.fakes;

import com.fidelreactlibrary.adapters.abstraction.DataConverter;

public final class DataConverterStub<D, C> implements DataConverter<D, C> {

    public D dataReceived;
    public C convertedDataToReturn;

    @Override
    public C getConvertedDataFor(D data) {
        dataReceived = data;
        return convertedDataToReturn;
    }
}
