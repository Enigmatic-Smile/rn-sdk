package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.Country;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ProgramTypeAdapter;

import org.json.JSONObject;

import java.util.Map;

public final class FidelSetupAdapter implements DataProcessor<ReadableMap>, DataOutput<Bitmap>, ConstantsProvider {

    private final DataProcessor<ReadableMap> imageAdapter;
    private final CountryAdapter countryAdapter;
    private final CardSchemesAdapter cardSchemesAdapter;
    private final ProgramTypeAdapter programTypeAdapter;

    public FidelSetupAdapter(DataProcessor<ReadableMap> imageAdapter, CountryAdapter countryAdapter,
            CardSchemesAdapter cardSchemesAdapter, ProgramTypeAdapter programTypeAdapter) {
        this.imageAdapter = imageAdapter;
        this.countryAdapter = countryAdapter;
        this.cardSchemesAdapter = cardSchemesAdapter;
        this.programTypeAdapter = programTypeAdapter;
    }

    @Override
    public void process(ReadableMap data) {
        if (data.hasKey(FidelSetupProperties.SDK_KEY.jsName())) {
            Fidel.sdkKey = data.getString(FidelSetupProperties.SDK_KEY.jsName());
        }
        if (data.hasKey(FidelSetupProperties.PROGRAM_ID.jsName())) {
            Fidel.programId = data.getString(FidelSetupProperties.PROGRAM_ID.jsName());
        }
        if (data.hasKey(FidelSetupProperties.PROGRAM_TYPE.jsName())) {
            String programTypeValue = data.getString(FidelSetupProperties.PROGRAM_TYPE.jsName());
            Fidel.programType = programTypeAdapter.parseProgramType(programTypeValue);
        }
        ReadableMap optionsMap = data.getMap(FidelSetupProperties.OPTIONS.jsName());
        if (optionsMap != null) {
            if (optionsMap.hasKey(FidelSetupProperties.Options.BANNER_IMAGE.jsName())) {
                imageAdapter.process(optionsMap.getMap(FidelSetupProperties.Options.BANNER_IMAGE.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupProperties.Options.ALLOWED_COUNTRIES.jsName())) {
                Fidel.allowedCountries = countryAdapter
                        .parseAllowedCountries(optionsMap.getArray(FidelSetupProperties.Options.ALLOWED_COUNTRIES.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupProperties.Options.DEFAULT_SELECTED_COUNTRY.jsName())) {
                Country defaultSelectedCountry = countryAdapter.countryWithJSValue(
                        optionsMap.getString(FidelSetupProperties.Options.DEFAULT_SELECTED_COUNTRY.jsName()));
                if (defaultSelectedCountry != null) {
                    Fidel.defaultSelectedCountry = defaultSelectedCountry;
                }
            }
            if (optionsMap.hasKey(FidelSetupProperties.Options.SUPPORTED_CARD_SCHEMES.jsName())) {
                Fidel.supportedCardSchemes = cardSchemesAdapter.cardSchemesWithReadableArray(
                        optionsMap.getArray(FidelSetupProperties.Options.SUPPORTED_CARD_SCHEMES.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupProperties.Options.THIRD_PARTY_VERIFICATION_CHOICE.jsName())) {
                Fidel.thirdPartyVerificationChoice = optionsMap
                        .getBoolean(FidelSetupProperties.Options.THIRD_PARTY_VERIFICATION_CHOICE.jsName());
            }
            if (optionsMap.hasKey(FidelSetupProperties.Options.META_DATA.jsName())) {
                ReadableMap metaDataMap = optionsMap.getMap(FidelSetupProperties.Options.META_DATA.jsName());
                if (metaDataMap != null) {
                    Fidel.metaData = new JSONObject(metaDataMap.toHashMap());
                } else {
                    Fidel.metaData = null;
                }
            }
        }

        ReadableMap consentTextMap = data.getMap(FidelSetupProperties.CONSENT_TEXT.jsName());
        if (consentTextMap != null) {
            if (consentTextMap.hasKey(FidelSetupProperties.ConsentText.COMPANY_NAME.jsName())) {
                Fidel.companyName = consentTextMap.getString(FidelSetupProperties.ConsentText.COMPANY_NAME.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupProperties.ConsentText.TERMS_AND_CONDITIONS_URL.jsName())) {
                Fidel.termsAndConditionsUrl = consentTextMap
                        .getString(FidelSetupProperties.ConsentText.TERMS_AND_CONDITIONS_URL.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupProperties.ConsentText.PRIVACY_POLICY_URL.jsName())) {
                Fidel.privacyPolicyUrl = consentTextMap
                        .getString(FidelSetupProperties.ConsentText.PRIVACY_POLICY_URL.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupProperties.ConsentText.PROGRAM_NAME.jsName())) {
                Fidel.programName = consentTextMap.getString(FidelSetupProperties.ConsentText.PROGRAM_NAME.jsName());
            }
            if (consentTextMap.hasKey(FidelSetupProperties.ConsentText.DELETE_INSTRUCTIONS.jsName())) {
                Fidel.deleteInstructions = consentTextMap
                        .getString(FidelSetupProperties.ConsentText.DELETE_INSTRUCTIONS.jsName());
            }
        }
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
        constants.putAll(programTypeAdapter.getConstants());
        return constants;
    }
}
