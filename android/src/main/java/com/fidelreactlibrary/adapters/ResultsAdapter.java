package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelapi.entities.EnrollmentErrorType;
import com.fidelapi.entities.FidelErrorType;
import com.fidelapi.entities.VerificationErrorType;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataAdapter;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ResultsAdapter implements DataAdapter<Object, WritableMap>, ConstantsProvider {

    private final ObjectFactory<WritableMap> writableMapFactory;
    private final CountryAdapter countryAdapter;
    private final CardSchemesAdapter cardSchemesAdapter;

    public ResultsAdapter(ObjectFactory<WritableMap> writableMapFactory,
                          CountryAdapter countryAdapter,
                          CardSchemesAdapter cardSchemesAdapter) {
        this.writableMapFactory = writableMapFactory;
        this.countryAdapter = countryAdapter;
        this.cardSchemesAdapter = cardSchemesAdapter;
    }

    @Override
    public WritableMap getAdaptedObjectFor(Object data) {
        if (data == null) {
            return null;
        }
        WritableMap map = writableMapFactory.create();
        for (Field field: data.getClass().getDeclaredFields()) {
            if (field.getType() == String.class) {
                String fieldValue = null;
                try {
                    fieldValue = (String)field.get(data);
                } catch (Throwable e) {
                    String getterMethodName = String.format("get%s", field.getName());
                    for (Method method: data.getClass().getDeclaredMethods()) {
                        if (method.getName().toLowerCase().equals(getterMethodName) && method.getReturnType() == String.class) {
                            try {
                                fieldValue = (String)method.invoke(data);
                            } catch (Throwable ignored) {}
                            break;
                        }
                    }
                }
                if (fieldValue != null) {
                    map.putString(field.getName(), fieldValue);
                } else {
                    map.putNull(field.getName());
                }
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                try {
                    map.putBoolean(field.getName(), field.getBoolean(data));
                } catch (Throwable e) {
                    map.putBoolean(field.getName(), false);
                }
            } else if (field.getType() == int.class) {
                try {
                    map.putInt(field.getName(), field.getInt(data));
                } catch (Throwable e) {
                    map.putInt(field.getName(), -1);
                }
            } else if (field.getType() == long.class) {
                try {
                    map.putDouble(field.getName(), field.getLong(data));
                } catch (Throwable e) {
                    map.putDouble(field.getName(), (long)-1);
                }
            } else if (field.getType() == JSONObject.class) {
                try {
                    JSONObject jsonObject = (JSONObject)field.get(data);
                    WritableMap mapToPut = this.getMapFor(jsonObject);
                    map.putMap(field.getName(), mapToPut);
                } catch (Throwable e) {
                    map.putNull(field.getName());
                }
            } else if (field.getType() == Country.class) {
                try {
                    String countryValue = countryAdapter.jsCountryValue((Country)field.get(data));
                    map.putString(field.getName(), countryValue);
                } catch (Throwable e) {
                    map.putNull(field.getName());
                }
            } else if (field.getType() == CardScheme.class) {
                try {
                    String cardSchemeValue = cardSchemesAdapter.jsValue((CardScheme)field.get(data));
                    map.putString(field.getName(), cardSchemeValue);
                } catch (Throwable e) {
                    map.putNull(field.getName());
                }
            } else if (field.getType() == FidelErrorType.class) {
                try {
                    FidelErrorType errorType = (FidelErrorType) field.get(data);
                    String jsErrorType = getJSErrorType(errorType);
                    map.putString(field.getName(), jsErrorType);
                    String jsErrorSubtype = getJSErrorSubtype(errorType);
                    if (jsErrorSubtype != null) {
                        map.putString("subtype", jsErrorSubtype);
                    }
                } catch (Throwable e) {
                    map.putString(field.getName(), "unknown");
                }
            }
        }
        return map;
    }

    @NonNull
    private String getJSErrorType(FidelErrorType errorType) {
        if (errorType == FidelErrorType.UserCanceled.INSTANCE) {
            return "userCanceled";
        } else if (errorType == FidelErrorType.SdkConfigurationError.INSTANCE) {
            return "sdkConfigurationError";
        } else if (errorType instanceof FidelErrorType.EnrollmentError) {
            return "enrollmentError";
        } else if (errorType instanceof FidelErrorType.VerificationError) {
            return "verificationError";
        }
        return "unknown";
    }

    @Nullable
    private String getJSErrorSubtype(FidelErrorType errorType) {
        if (errorType instanceof FidelErrorType.EnrollmentError) {
            FidelErrorType.EnrollmentError enrollmentError = (FidelErrorType.EnrollmentError)errorType;
            return enrollmentErrorTypeJSValue(enrollmentError.type);
        } else if (errorType instanceof FidelErrorType.VerificationError) {
            FidelErrorType.VerificationError verificationError = (FidelErrorType.VerificationError)errorType;
            return verificationErrorTypeJSValue(verificationError.type);
        }
        return null;
    }

    private String enrollmentErrorTypeJSValue(EnrollmentErrorType enrollmentErrorType) {
        switch (enrollmentErrorType) {
            case CARD_ALREADY_EXISTS:
                return "cardAlreadyExists";
            case INVALID_PROGRAM_ID:
                return "invalidProgramId";
            case INVALID_SDK_KEY:
                return "invalidSdkKey";
            case INEXISTENT_PROGRAM:
                return "inexistentProgram";
            case UNEXPECTED:
                return "unexpected";
        }
        return "unexpected";
    }

    private String verificationErrorTypeJSValue(VerificationErrorType enrollmentErrorType) {
        switch (enrollmentErrorType) {
            case UNEXPECTED:
                return "unexpected";
            case UNAUTHORIZED:
                return "unauthorized";
            case INCORRECT_AMOUNT:
                return "incorrectAmount";
            case MAXIMUM_ATTEMPTS_REACHED:
                return "maximumAttemptsReached";
            case CARD_ALREADY_VERIFIED:
                return "cardAlreadyVerified";
            case CARD_NOT_FOUND:
                return "cardNotFound";
            case VERIFICATION_NOT_FOUND:
                return "verificationNotFound";
            case GENERIC_ERROR:
                return "genericError";
        }
        return "unexpected";
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> errorConstants = new HashMap<>();
        Map<String, String> errorTypeConstants = new HashMap<>();
        putErrorTypeConstant(errorTypeConstants, FidelErrorType.UserCanceled.INSTANCE);
        putErrorTypeConstant(errorTypeConstants, FidelErrorType.SdkConfigurationError.INSTANCE);
        putErrorTypeConstant(errorTypeConstants, new FidelErrorType.EnrollmentError(EnrollmentErrorType.CARD_ALREADY_EXISTS));
        putErrorTypeConstant(errorTypeConstants, new FidelErrorType.VerificationError(VerificationErrorType.CARD_ALREADY_VERIFIED));
        errorConstants.put("ErrorType", errorTypeConstants);

        Map<String, String> enrollmentErrorTypeConstants = new HashMap<>();
        putEnrollmentErrorTypeConstant(enrollmentErrorTypeConstants, EnrollmentErrorType.CARD_ALREADY_EXISTS);
        putEnrollmentErrorTypeConstant(enrollmentErrorTypeConstants, EnrollmentErrorType.INEXISTENT_PROGRAM);
        putEnrollmentErrorTypeConstant(enrollmentErrorTypeConstants, EnrollmentErrorType.INVALID_PROGRAM_ID);
        putEnrollmentErrorTypeConstant(enrollmentErrorTypeConstants, EnrollmentErrorType.INVALID_SDK_KEY);
        putEnrollmentErrorTypeConstant(enrollmentErrorTypeConstants, EnrollmentErrorType.UNEXPECTED);
        errorConstants.put("EnrollmentErrorType", enrollmentErrorTypeConstants);

        Map<String, String> verificationErrorTypeConstants = new HashMap<>();
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.GENERIC_ERROR);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.UNEXPECTED);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.CARD_ALREADY_VERIFIED);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.VERIFICATION_NOT_FOUND);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.MAXIMUM_ATTEMPTS_REACHED);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.INCORRECT_AMOUNT);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.UNAUTHORIZED);
        putVerificationErrorTypeConstant(verificationErrorTypeConstants, VerificationErrorType.CARD_NOT_FOUND);
        errorConstants.put("VerificationErrorType", verificationErrorTypeConstants);
        return errorConstants;
    }

    private void putErrorTypeConstant(Map<String, String> map, FidelErrorType errorType) {
        String errorTypeJSValue = getJSErrorType(errorType);
        map.put(errorTypeJSValue, errorTypeJSValue);
    }

    private void putEnrollmentErrorTypeConstant(Map<String, String> map, EnrollmentErrorType errorType) {
        String errorTypeJSValue = enrollmentErrorTypeJSValue(errorType);
        map.put(errorTypeJSValue, errorTypeJSValue);
    }

    private void putVerificationErrorTypeConstant(Map<String, String> map, VerificationErrorType errorType) {
        String errorTypeJSValue = verificationErrorTypeJSValue(errorType);
        map.put(errorTypeJSValue, errorTypeJSValue);
    }

    private WritableMap getMapFor(JSONObject json) {
        Iterator<String> jsonKeyIterator = json.keys();
        WritableMap map = writableMapFactory.create();
        while (jsonKeyIterator.hasNext()) {
            String key = jsonKeyIterator.next();
            try {
                Object value = json.get(key);
                Class<?> valueClass = value.getClass();
                if (valueClass == String.class) {
                    map.putString(key, (String)value);
                } else if (valueClass == Boolean.class) {
                    map.putBoolean(key, (boolean)value);
                } else if (valueClass == Integer.class) {
                    map.putInt(key, (int)value);
                } else if (valueClass == JSONObject.class) {
                    WritableMap mapToPut = this.getMapFor((JSONObject)value);
                    map.putMap(key, mapToPut);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
