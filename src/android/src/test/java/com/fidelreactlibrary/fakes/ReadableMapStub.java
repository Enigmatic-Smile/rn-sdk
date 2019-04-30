package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReadableMapStub implements ReadableMap {

    public String keyNameCheckedFor;
    public String keyNameVerifiedNullFor;
    public String keyNameAskedFor;
    private ReadableMap mapToReturn;
    public String hasKeyString;
    public String isNullString;

    public ReadableMapStub() {}
    public ReadableMapStub(ReadableMap mapToReturn) {
        this.mapToReturn = mapToReturn;
    }
    public ReadableMapStub(String hasKeyString, String isNullString) {
        this.hasKeyString = hasKeyString;
        this.isNullString = isNullString;
    }

    @Override
    public boolean hasKey(@Nonnull String name) {
        keyNameCheckedFor = name;
        return hasKeyString.equals(name);
    }

    @Override
    public boolean isNull(@Nonnull String name) {
        keyNameVerifiedNullFor = name;
        return isNullString.equals(name);
    }

    @Override
    public boolean getBoolean(@Nonnull String name) {
        return false;
    }

    @Override
    public double getDouble(@Nonnull String name) {
        return 0;
    }

    @Override
    public int getInt(@Nonnull String name) {
        return 0;
    }

    @Nullable
    @Override
    public String getString(@Nonnull String name) {
        return null;
    }

    @Nullable
    @Override
    public ReadableArray getArray(@Nonnull String name) {
        return null;
    }

    @Nullable
    @Override
    public ReadableMap getMap(@Nonnull String name) {
        keyNameAskedFor = name;
        return mapToReturn;
    }

    @Nonnull
    @Override
    public Dynamic getDynamic(@Nonnull String name) {
        return null;
    }

    @Nonnull
    @Override
    public ReadableType getType(@Nonnull String name) {
        return null;
    }

    @Nonnull
    @Override
    public ReadableMapKeySetIterator keySetIterator() {
        return null;
    }

    @Nonnull
    @Override
    public HashMap<String, Object> toHashMap() {
        return null;
    }
}
