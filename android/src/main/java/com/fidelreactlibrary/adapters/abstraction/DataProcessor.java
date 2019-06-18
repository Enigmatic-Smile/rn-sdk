package com.fidelreactlibrary.adapters.abstraction;

public interface DataProcessor<RawDataType> {
    void process(RawDataType data);
}
