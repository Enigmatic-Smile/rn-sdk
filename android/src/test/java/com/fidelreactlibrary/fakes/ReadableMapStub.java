package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.fidelreactlibrary.adapters.CardVerificationConfigurationProperties;
import com.fidelreactlibrary.adapters.FidelSetupProperties;

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

    public ReadableMapStub() {
    }

    public static ReadableMapStub mapWithNoKey() {
        return new ReadableMapStub();
    }

    public static ReadableMapStub mapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupProperties.values()).map(FidelSetupProperties::jsName)
                .toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.SDK_KEY.jsName(), "pk_test_some_sdk_key");
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.PROGRAM_ID.jsName(), "some test program ID");
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.PROGRAM_TYPE.jsName(), "some test program type");
        ReadableMapStub optionsReadableMap = ReadableMapStub.optionsMapWithAllValidSetupKeys();
        mapStub.mapsForKeysToReturn.put(FidelSetupProperties.OPTIONS.jsName(), optionsReadableMap);
        ReadableMapStub consentTextReadableMap = ReadableMapStub.consentTextMapWithAllValidSetupKeys();
        mapStub.mapsForKeysToReturn.put(FidelSetupProperties.CONSENT_TEXT.jsName(), consentTextReadableMap);
        return mapStub;
    }

    private static ReadableMapStub optionsMapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupProperties.Options.values())
                .map(FidelSetupProperties.Options::jsName)
                .toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.mapsForKeysToReturn.put(FidelSetupProperties.Options.BANNER_IMAGE.jsName(), new ReadableMapStub());
        mapStub.readableArraysToReturn.put(FidelSetupProperties.Options.ALLOWED_COUNTRIES.jsName(),
                new JavaOnlyArray());
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.Options.DEFAULT_SELECTED_COUNTRY.jsName(), "japan");
        mapStub.readableArraysToReturn.put(FidelSetupProperties.Options.SUPPORTED_CARD_SCHEMES.jsName(),
                new JavaOnlyArray());
        mapStub.boolToReturn = false;
        ReadableMapStub metaDataMap = new ReadableMapStub();
        metaDataMap.hashMapToReturn = new HashMap<>();
        mapStub.mapsForKeysToReturn.put(FidelSetupProperties.Options.META_DATA.jsName(), metaDataMap);
        return mapStub;
    }

    private static ReadableMapStub consentTextMapWithAllValidSetupKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(FidelSetupProperties.ConsentText.values())
                .map(FidelSetupProperties.ConsentText::jsName)
                .toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.ConsentText.COMPANY_NAME.jsName(),
                "some test company name");
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.ConsentText.TERMS_AND_CONDITIONS_URL.jsName(),
                "some test terms and conditions url");
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.ConsentText.PRIVACY_POLICY_URL.jsName(),
                "some test privacy policy url");
        mapStub.stringForKeyToReturn.put(FidelSetupProperties.ConsentText.PROGRAM_NAME.jsName(),
                "some test program name");
        return mapStub;
    }

    public static ReadableMapStub withEmptyValueForKey(FidelSetupProperties key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.stringForKeyToReturn.put(key.jsName(), "");
        return mapStub;
    }

    public static ReadableMapStub withoutKey(FidelSetupProperties key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.hasKeyStrings.remove(key.jsName());
        mapStub.stringForKeyToReturn.remove(key.jsName());
        mapStub.mapsForKeysToReturn.remove(key.jsName());
        mapStub.readableArraysToReturn.remove(key.jsName());
        return mapStub;
    }

    public static ReadableMapStub withoutOptionsKey(FidelSetupProperties.Options optionsKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMapStub = (ReadableMapStub) mapStub.getMap(FidelSetupProperties.OPTIONS.jsName());
        assert optionsMapStub != null;
        optionsMapStub.hasKeyStrings.remove(optionsKey.jsName());
        optionsMapStub.stringForKeyToReturn.remove(optionsKey.jsName());
        optionsMapStub.mapsForKeysToReturn.remove(optionsKey.jsName());
        optionsMapStub.readableArraysToReturn.remove(optionsKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub withoutConsentTextKey(FidelSetupProperties.ConsentText consentTextKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub consentTextMapStub = (ReadableMapStub) mapStub
                .getMap(FidelSetupProperties.CONSENT_TEXT.jsName());
        assert consentTextMapStub != null;
        consentTextMapStub.hasKeyStrings.remove(consentTextKey.jsName());
        consentTextMapStub.stringForKeyToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.mapsForKeysToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.readableArraysToReturn.remove(consentTextKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForKey(FidelSetupProperties key) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        mapStub.isNullStrings.remove(key.jsName());
        mapStub.stringForKeyToReturn.remove(key.jsName());
        mapStub.mapsForKeysToReturn.remove(key.jsName());
        mapStub.readableArraysToReturn.remove(key.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForOptionKey(FidelSetupProperties.Options optionKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub optionsMapStub = (ReadableMapStub) mapStub.getMap(FidelSetupProperties.OPTIONS.jsName());
        assert optionsMapStub != null;
        optionsMapStub.isNullStrings.add(optionKey.jsName());
        optionsMapStub.stringForKeyToReturn.remove(optionKey.jsName());
        optionsMapStub.mapsForKeysToReturn.remove(optionKey.jsName());
        optionsMapStub.readableArraysToReturn.remove(optionKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub withNullValueForConsentTextKey(FidelSetupProperties.ConsentText consentTextKey) {
        ReadableMapStub mapStub = ReadableMapStub.mapWithAllValidSetupKeys();
        ReadableMapStub consentTextMapStub = (ReadableMapStub) mapStub
                .getMap(FidelSetupProperties.CONSENT_TEXT.jsName());
        assert consentTextMapStub != null;
        consentTextMapStub.isNullStrings.add(consentTextKey.jsName());
        consentTextMapStub.stringForKeyToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.mapsForKeysToReturn.remove(consentTextKey.jsName());
        consentTextMapStub.readableArraysToReturn.remove(consentTextKey.jsName());
        return mapStub;
    }

    public static ReadableMapStub cardConfigWithAllValidConfigKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(CardVerificationConfigurationProperties.values())
                .map(CardVerificationConfigurationProperties::jsName).toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.stringForKeyToReturn.put(CardVerificationConfigurationProperties.ID.jsName(), "123");
        mapStub.stringForKeyToReturn.put(CardVerificationConfigurationProperties.CONSENT_ID.jsName(), "456");
        mapStub.stringForKeyToReturn.put(CardVerificationConfigurationProperties.LAST_4_DIGITS.jsName(), "4567");
        return mapStub;
    }

    public static ReadableMapStub cardConfigWithAllInValidConfigKeys() {
        ReadableMapStub mapStub = new ReadableMapStub();
        String[] keyJsNames = Arrays.stream(CardVerificationConfigurationProperties.values())
                .map(CardVerificationConfigurationProperties::jsName).toArray(String[]::new);
        mapStub.hasKeyStrings.addAll(Arrays.asList(keyJsNames));
        mapStub.stringForKeyToReturn.put(CardVerificationConfigurationProperties.ID.jsName(), null);
        mapStub.stringForKeyToReturn.put(CardVerificationConfigurationProperties.CONSENT_ID.jsName(), null);
        mapStub.stringForKeyToReturn.put(CardVerificationConfigurationProperties.LAST_4_DIGITS.jsName(), null);
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
