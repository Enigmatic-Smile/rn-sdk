package com.fidelreactlibrary.fakes;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JavaOnlyArray;
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
    public HashMap<String, ReadableMap> mapsForKeysToReturn = new HashMap<>();
    public HashMap<String, Object> hashMapToReturn;
    public HashMap<String, String> stringForKeyToReturn = new HashMap<>();
    public ReadableArray readableArrayToReturn = new JavaOnlyArray();
    public boolean boolToReturn;
    public int intToReturn;
    private String hasKeyString = "";
    private String isNullString = "";

    public ReadableMapStub() {}
    private ReadableMapStub(String hasKeyString, String isNullString) {
        this.hasKeyString = hasKeyString;
        this.isNullString = isNullString;
    }

    public static ReadableMapStub mapWithNoKey() {
        return new ReadableMapStub("", "");
    }

    public static ReadableMapStub mapWithExistingKey(String existingKey) {
        return new ReadableMapStub(existingKey, "");
    }

    public static ReadableMapStub mapWithExistingKeyButNoValue(String existingKey) {
        return new ReadableMapStub(existingKey, existingKey);
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
        keyNamesAskedFor.add(name);
        return intToReturn;
    }

    @Nullable
    @Override
    public String getString(@Nonnull String name) {
        keyNamesAskedFor.add(name);
        return stringForKeyToReturn.get(name);
    }

    @Nullable
    @Override
    public ReadableArray getArray(@Nonnull String name) {
        keyNamesAskedFor.add(name);
        return readableArrayToReturn;
    }

    @Nullable
    @Override
    public ReadableMap getMap(@Nonnull String name) {
        keyNamesAskedFor.add(name);
        return mapsForKeysToReturn.get(name);
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
        return hashMapToReturn;
    }
}
