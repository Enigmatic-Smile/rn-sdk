package com.fidelreactlibrary.adapters.abstraction;

public interface DataAdapter<DataType, AdaptedDataType> {
    AdaptedDataType getAdaptedObjectFor(DataType data);
}
