package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.util.ArrayList;

public class ReadableArrayStub implements ReadableArray {

    private final int size;
    private final int[] values;

    public ReadableArrayStub(int size, int[] values) {
        this.size = size;
        this.values = values;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isNull(int index) {
        return false;
    }

    @Override
    public boolean getBoolean(int index) {
        return false;
    }

    @Override
    public double getDouble(int index) {
        return 0;
    }

    @Override
    public int getInt(int index) {
        return values[index];
    }

    @NonNull
    @Override
    public String getString(int index) {
        return null;
    }

    @NonNull
    @Override
    public ReadableArray getArray(int index) {
        return null;
    }

    @NonNull
    @Override
    public ReadableMap getMap(int index) {
        return null;
    }

    @NonNull
    @Override
    public Dynamic getDynamic(int index) {
        return null;
    }

    @NonNull
    @Override
    public ReadableType getType(int index) {
        return null;
    }

    @NonNull
    @Override
    public ArrayList<Object> toArrayList() {
        return null;
    }
}
