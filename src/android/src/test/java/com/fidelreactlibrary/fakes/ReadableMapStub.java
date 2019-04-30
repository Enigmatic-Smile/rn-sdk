package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReadableMapStub implements ReadableMap {

    public List<String> keyNamesCheckedFor = new ArrayList<>();
    public List<String> keyNamesVerifiedNullFor = new ArrayList<>();
    public List<String> keyNamesAskedFor = new ArrayList<>();
    public ReadableMap mapToReturn;
    public boolean boolToReturn;
    private String hasKeyString = "";
    private String isNullString = "";

    public ReadableMapStub() {}
    public ReadableMapStub(String hasKeyString, String isNullString) {
        this.hasKeyString = hasKeyString;
        this.isNullString = isNullString;
    }

    @Override
    public boolean hasKey(@Nonnull String name) {
        keyNamesCheckedFor.add(name);
        return hasKeyString.equals(name);
    }

    @Override
    public boolean isNull(@Nonnull String name) {
        keyNamesVerifiedNullFor.add(name);
        return isNullString.equals(name);
    }

    @Override
    public boolean getBoolean(@Nonnull String name) {
        keyNamesAskedFor.add(name);
        return boolToReturn;
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
        keyNamesAskedFor.add(name);
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
