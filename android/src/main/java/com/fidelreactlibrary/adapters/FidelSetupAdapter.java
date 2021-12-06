package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.EnrollmentErrorType;
import com.fidelapi.entities.FidelError;
import com.fidelapi.entities.FidelErrorType;
import com.fidelapi.entities.FidelResult;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import org.json.JSONObject;

import java.util.Map;

public final class FidelSetupAdapter implements DataProcessor<ReadableMap>, DataOutput<Bitmap>, ConstantsProvider {

    private final DataProcessor<ReadableMap> imageAdapter;
    private final CountryAdapter countryAdapter;
    private final CardSchemesAdapter cardSchemesAdapter;

    public FidelSetupAdapter(DataProcessor<ReadableMap> imageAdapter, CountryAdapter countryAdapter, CardSchemesAdapter cardSchemesAdapter) {
        this.imageAdapter = imageAdapter;
        this.countryAdapter = countryAdapter;
        this.cardSchemesAdapter = cardSchemesAdapter;
    }

    @Override
    public void process(ReadableMap data) {
        Fidel.sdkKey = data.getString(FidelSetupKeys.SDK_KEY.jsName());
        Fidel.programId = data.getString(FidelSetupKeys.PROGRAM_ID.jsName());
        Fidel.companyName = data.getString(FidelSetupKeys.COMPANY_NAME.jsName());
        ReadableMap optionsMap = data.getMap(FidelSetupKeys.OPTIONS.jsName());
        if (optionsMap != null) {
            imageAdapter.process(optionsMap.getMap(FidelSetupKeys.Options.BANNER_IMAGE.jsName()));
            if (optionsMap.hasKey(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName())) {
                Fidel.allowedCountries = countryAdapter.parseAllowedCountries(optionsMap.getArray(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName())) {
                Fidel.supportedCardSchemes = cardSchemesAdapter.cardSchemesWithReadableArray(optionsMap.getArray(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.SHOULD_AUTO_SCAN.jsName())) {
                Fidel.shouldAutoScanCard = optionsMap.getBoolean(FidelSetupKeys.Options.SHOULD_AUTO_SCAN.jsName());
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.META_DATA.jsName())) {
                ReadableMap metaDataMap = optionsMap.getMap(FidelSetupKeys.Options.META_DATA.jsName());
                if (metaDataMap != null) {
                    Fidel.metaData = new JSONObject(metaDataMap.toHashMap());
                } else {
                    Fidel.metaData = null;
                }
            }
        }

        ReadableMap consentTextMap = data.getMap(FidelSetupKeys.CONSENT_TEXT.jsName());
        if (consentTextMap != null) {
            if (consentTextMap.hasKey(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName())) {
                Fidel.termsAndConditionsUrl = consentTextMap.getString(FidelSetupKeys.ConsentText.TERMS_AND_CONDITIONS_URL.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupKeys.ConsentText.PRIVACY_POLICY_URL.jsName())) {
                Fidel.privacyPolicyUrl = consentTextMap.getString(FidelSetupKeys.ConsentText.PRIVACY_POLICY_URL.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupKeys.ConsentText.PROGRAM_NAME.jsName())) {
                Fidel.programName = consentTextMap.getString(FidelSetupKeys.ConsentText.PROGRAM_NAME.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupKeys.ConsentText.DELETE_INSTRUCTIONS.jsName())) {
                Fidel.deleteInstructions = consentTextMap.getString(FidelSetupKeys.ConsentText.DELETE_INSTRUCTIONS.jsName());
            }
        }
        
        Fidel.onResult = (result -> {
            if (result instanceof FidelResult.Enrollment) {
                Log.d("Fidel.Debug", ((FidelResult.Enrollment) result).getEnrollmentResult().cardId);
            } else if (result instanceof FidelResult.VerificationSuccessful) {
                Log.d("Fidel.Debug", "The card verification was successful");
            } else {
                FidelError error = ((FidelResult.Error) result).getError();
                Log.e("Fidel.Debug", error.getMessage());
                if (error.type == FidelErrorType.UserCanceled.INSTANCE) {
                    Log.e("Fidel.Debug", "the user cancelled the flow");
                } else if (error.type == FidelErrorType.SdkConfigurationError.INSTANCE) {
                    Log.e("Fidel.Debug", "Fidel should be configured correctly");
                } else if (error.type instanceof FidelErrorType.EnrollmentError) {
                    FidelErrorType.EnrollmentError enrollmentError = (FidelErrorType.EnrollmentError)error.type;
                    if (enrollmentError.type == EnrollmentErrorType.CARD_ALREADY_EXISTS) {
                        Log.e("Fidel.Debug", "card was already enrolled");
                    } else {
                        Log.e("Fidel.Debug", "another enrollment error");
                    }
                }
            }
        });
    }

    @Override
    public void output(Bitmap data) {
        Fidel.bannerImage = data;
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = countryAdapter.getConstants();
        constants.putAll(cardSchemesAdapter.getConstants());
        return constants;
    }
}
