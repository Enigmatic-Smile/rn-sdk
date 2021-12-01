package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.FidelSetupKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReadableMapStub implements ReadableMap {

    public List<String> keyNamesCheckedFor = new ArrayList<>();
    public List<String> keyNamesVerifiedNullFor = new ArrayList<>();
    public List<String> keyNamesAskedFor = new ArrayList<>();
    public HashMap<String, ReadableMap> mapsForKeysToReturn = new HashMap<>();
    public HashMap<String, Object> hashMapToReturn;
    public HashMap<String, String> stringForKeyToReturn = new HashMap<>();
    public HashMap<String, ReadableArray> readableArraysToReturn = new HashMap<>();
    public boolean boolToReturn;
    private final List<String> hasKeyStrings = new ArrayList<>();
    private final List<String> isNullStrings = new ArrayList<>();

    public ReadableMapStub() {}
    private ReadableMapStub(String hasKeyString, String isNullString) {
        this.hasKeyStrings.add(hasKeyString);
        this.isNullStrings.add(isNullString);
    }

    public static ReadableMapStub mapWithNoKey() {
        return new ReadableMapStub();
    }

    public static ReadableMapStub mapWithExistingKey(String existingKey) {
        return new ReadableMapStub(existingKey, "");
    }

    public static ReadableMapStub mapWithExistingKeyButNoValue(String existingKey) {
        return new ReadableMapStub(existingKey, existingKey);
    }

    public static ReadableMapStub mapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupKeys.values()).map(FidelSetupKeys::jsName).toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.stringForKeyToReturn.put(FidelSetupKeys.SDK_KEY.jsName(), "pk_test_some_sdk_key");
        mapStub.stringForKeyToReturn.put(FidelSetupKeys.PROGRAM_ID.jsName(), "some test program ID");
        mapStub.stringForKeyToReturn.put(FidelSetupKeys.COMPANY_NAME.jsName(), "some test company name");
        ReadableMapStub optionsReadableMap = ReadableMapStub.optionsMapWithAllValidSetupKeys();
        mapStub.mapsForKeysToReturn.put(FidelSetupKeys.OPTIONS.jsName(), optionsReadableMap);
        return mapStub;
    }

    private static ReadableMapStub optionsMapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupKeys.Options.values()).map(FidelSetupKeys.Options::jsName).toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        ReadableMapStub bannerImageReadableMap = new ReadableMapStub();
        mapStub.mapsForKeysToReturn.put(FidelSetupKeys.Options.BANNER_IMAGE.jsName(), bannerImageReadableMap);
        mapStub.readableArraysToReturn.put(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName(), new JavaOnlyArray());
        return mapStub;
    }

    public static ReadableMapStub withEmptyValueForKey(FidelSetupKeys key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.stringForKeyToReturn.put(key.jsName(), "");
        return mapStub;
    }

    public static ReadableMapStub withoutKey(FidelSetupKeys key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.hasKeyStrings.remove(key.jsName());
        mapStub.stringForKeyToReturn.remove(key.jsName());
        mapStub.mapsForKeysToReturn.remove(key.jsName());
        return mapStub;
    }

    public static ReadableMapStub withoutOptionsKey(FidelSetupKeys.Options optionsKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMapStub = (ReadableMapStub)mapStub.getMap(FidelSetupKeys.OPTIONS.jsName());
        assert optionsMapStub != null;
        optionsMapStub.hasKeyStrings.remove(optionsKey.jsName());
        switch (optionsKey) {
            case BANNER_IMAGE:
                optionsMapStub.mapsForKeysToReturn.remove(optionsKey.jsName());
                break;
            default:
                break;
        }
        return mapStub;
    }

    public static ReadableMapStub withNullValueForKey(FidelSetupKeys key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.isNullStrings.remove(key.jsName());
        mapStub.stringForKeyToReturn.remove(key.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForOptionKey(FidelSetupKeys.Options optionKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMapStub = (ReadableMapStub)mapStub.getMap(FidelSetupKeys.OPTIONS.jsName());
        assert optionsMapStub != null;
        optionsMapStub.isNullStrings.add(optionKey.jsName());
        optionsMapStub.mapsForKeysToReturn.remove(optionKey.jsName());
        return mapStub;
    }

    public void putString(String key, String value) {
        stringForKeyToReturn.put(key, value);
    }

    @Override
    public boolean hasKey(@Nonnull String name) {
        keyNamesCheckedFor.add(name);
        return hasKeyStrings.contains(name);
    }

    @Override
    public boolean isNull(@Nonnull String name) {
        keyNamesVerifiedNullFor.add(name);
        return isNullStrings.contains(name);
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
        return -1;
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
        return readableArraysToReturn.get(name);
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

    @NonNull
    @Override
    public Iterator<Map.Entry<String, Object>> getEntryIterator() {
        return null;
    }

    @Nonnull
    @Override
    public HashMap<String, Object> toHashMap() {
        return hashMapToReturn;
    }
}
