package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.fidelreactlibrary.adapters.FidelSetupKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReadableMapStub implements ReadableMap {

    public List<String> keyNamesCheckedFor = new ArrayList<>();
    public List<String> keyNamesVerifiedNullFor = new ArrayList<>();
    public List<String> keyNamesAskedValueFor = new ArrayList<>();
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
        ReadableMapStub consentTextReadableMap = ReadableMapStub.consentTextMapWithAllValidSetupKeys();
        mapStub.mapsForKeysToReturn.put(FidelSetupKeys.CONSENT_TEXT.jsName(), consentTextReadableMap);
        return mapStub;
    }

    private static ReadableMapStub optionsMapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupKeys.Options.values()).map(FidelSetupKeys.Options::jsName).toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.mapsForKeysToReturn.put(FidelSetupKeys.Options.BANNER_IMAGE.jsName(), new ReadableMapStub());
        mapStub.readableArraysToReturn.put(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName(), new JavaOnlyArray());
        mapStub.readableArraysToReturn.put(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName(), new JavaOnlyArray());
        mapStub.boolToReturn = false;
        ReadableMapStub metaDataMap = new ReadableMapStub();
        metaDataMap.hashMapToReturn = new HashMap<>();
        mapStub.mapsForKeysToReturn.put(FidelSetupKeys.Options.META_DATA.jsName(), metaDataMap);
        return mapStub;
    }

    private static ReadableMapStub consentTextMapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupKeys.ConsentText.values()).map(FidelSetupKeys.ConsentText::jsName).toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.stringForKeyToReturn.put(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName(), "some test terms and conditions url");
        mapStub.stringForKeyToReturn.put(FidelSetupKeys.ConsentText.PRIVACY_POLICY_URL.jsName(), "some test privacy policy url");
        mapStub.stringForKeyToReturn.put(FidelSetupKeys.ConsentText.PROGRAM_NAME.jsName(), "some test program name");
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
        mapStub.readableArraysToReturn.remove(key.jsName());
        return mapStub;
    }

    public static ReadableMapStub withoutOptionsKey(FidelSetupKeys.Options optionsKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMapStub = (ReadableMapStub)mapStub.getMap(FidelSetupKeys.OPTIONS.jsName());
        assert optionsMapStub != null;
        optionsMapStub.hasKeyStrings.remove(optionsKey.jsName());
        optionsMapStub.stringForKeyToReturn.remove(optionsKey.jsName());
        optionsMapStub.mapsForKeysToReturn.remove(optionsKey.jsName());
        optionsMapStub.readableArraysToReturn.remove(optionsKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub withoutConsentTextKey(FidelSetupKeys.ConsentText consentTextKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub consentTextMapStub = (ReadableMapStub)mapStub.getMap(FidelSetupKeys.CONSENT_TEXT.jsName());
        assert consentTextMapStub != null;
        consentTextMapStub.hasKeyStrings.remove(consentTextKey.jsName());
        consentTextMapStub.stringForKeyToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.mapsForKeysToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.readableArraysToReturn.remove(consentTextKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForKey(FidelSetupKeys key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.isNullStrings.remove(key.jsName());
        mapStub.stringForKeyToReturn.remove(key.jsName());
        mapStub.mapsForKeysToReturn.remove(key.jsName());
        mapStub.readableArraysToReturn.remove(key.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForOptionKey(FidelSetupKeys.Options optionKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMapStub = (ReadableMapStub)mapStub.getMap(FidelSetupKeys.OPTIONS.jsName());
        assert optionsMapStub != null;
        optionsMapStub.isNullStrings.add(optionKey.jsName());
        optionsMapStub.stringForKeyToReturn.remove(optionKey.jsName());
        optionsMapStub.mapsForKeysToReturn.remove(optionKey.jsName());
        optionsMapStub.readableArraysToReturn.remove(optionKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForConsentTextKey(FidelSetupKeys.ConsentText consentTextKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub consentTextMapStub = (ReadableMapStub)mapStub.getMap(FidelSetupKeys.CONSENT_TEXT.jsName());
        assert consentTextMapStub != null;
        consentTextMapStub.isNullStrings.add(consentTextKey.jsName());
        consentTextMapStub.stringForKeyToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.mapsForKeysToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.readableArraysToReturn.remove(consentTextKey.jsName());
        return mapStub;
    }

    public void putString(String key, String value) {
        stringForKeyToReturn.put(key, value);
    }

    @Override
    public boolean hasKey(@NonNull String name) {
        keyNamesCheckedFor.add(name);
        return hasKeyStrings.contains(name);
    }

    @Override
    public boolean isNull(@NonNull String name) {
        keyNamesVerifiedNullFor.add(name);
        return isNullStrings.contains(name);
    }

    @Override
    public boolean getBoolean(@NonNull String name) {
        keyNamesAskedValueFor.add(name);
        return boolToReturn;
    }

    @Override
    public double getDouble(@NonNull String name) {
        return 0;
    }

    @Override
    public int getInt(@NonNull String name) {
        return -1;
    }

    @Nullable
    @Override
    public String getString(@NonNull String name) {
        keyNamesAskedValueFor.add(name);
        return stringForKeyToReturn.get(name);
    }

    @Nullable
    @Override
    public ReadableArray getArray(@NonNull String name) {
        keyNamesAskedValueFor.add(name);
        return readableArraysToReturn.get(name);
    }

    @Nullable
    @Override
    public ReadableMap getMap(@NonNull String name) {
        keyNamesAskedValueFor.add(name);
        return mapsForKeysToReturn.get(name);
    }

    @NonNull
    @Override
    public Dynamic getDynamic(@NonNull String name) {
        return null;
    }

    @NonNull
    @Override
    public ReadableType getType(@NonNull String name) {
        return null;
    }

    @NonNull
    @Override
    public ReadableMapKeySetIterator keySetIterator() {
        return null;
    }

    @NonNull
    @Override
    public Iterator<Map.Entry<String, Object>> getEntryIterator() {
        return null;
    }

    @NonNull
    @Override
    public HashMap<String, Object> toHashMap() {
        return hashMapToReturn;
    }
}
