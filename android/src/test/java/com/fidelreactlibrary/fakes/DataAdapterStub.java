package com.fidelreactlibrary.fakes;

import com.fidelreactlibrary.adapters.abstraction.DataAdapter;

public final class DataAdapterStub<D, C> implements DataAdapter<D, C> {

    public D dataReceived;
    public C convertedDataToReturn;

    @Override
    public C getAdaptedObjectFor(D data) {
        dataReceived = data;
        return convertedDataToReturn;
    }
}
