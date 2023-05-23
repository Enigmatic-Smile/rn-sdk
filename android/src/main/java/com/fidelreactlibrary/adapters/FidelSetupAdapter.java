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
        if (data.hasKey(FidelSetupKeys.SDK_KEY.jsName())) {
            Fidel.sdkKey = data.getString(FidelSetupKeys.SDK_KEY.jsName());
        }
        if (data.hasKey(FidelSetupKeys.PROGRAM_ID.jsName())) {
            Fidel.programId = data.getString(FidelSetupKeys.PROGRAM_ID.jsName());
        }
        if (data.hasKey(FidelSetupKeys.PROGRAM_TYPE.jsName())) {
            String programTypeValue = data.getString(FidelSetupKeys.PROGRAM_TYPE.jsName());
            Fidel.programType = programTypeAdapter.parseProgramType(programTypeValue);
        }
        ReadableMap optionsMap = data.getMap(FidelSetupKeys.OPTIONS.jsName());
        if (optionsMap != null) {
            if (optionsMap.hasKey(FidelSetupKeys.Options.BANNER_IMAGE.jsName())) {
                imageAdapter.process(optionsMap.getMap(FidelSetupKeys.Options.BANNER_IMAGE.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName())) {
                Fidel.allowedCountries = countryAdapter.parseAllowedCountries(optionsMap.getArray(FidelSetupKeys.Options.ALLOWED_COUNTRIES.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.DEFAULT_SELECTED_COUNTRY.jsName())) {
                Country defaultSelectedCountry = countryAdapter.countryWithJSValue(optionsMap.getString(FidelSetupKeys.Options.DEFAULT_SELECTED_COUNTRY.jsName()));
                if (defaultSelectedCountry != null) {
                    Fidel.defaultSelectedCountry = defaultSelectedCountry;
                }
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName())) {
                Fidel.supportedCardSchemes = cardSchemesAdapter.cardSchemesWithReadableArray(optionsMap.getArray(FidelSetupKeys.Options.SUPPORTED_CARD_SCHEMES.jsName()));
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.SHOULD_AUTO_SCAN.jsName())) {
                Fidel.shouldAutoScanCard = optionsMap.getBoolean(FidelSetupKeys.Options.SHOULD_AUTO_SCAN.jsName());
            }
            if (optionsMap.hasKey(FidelSetupKeys.Options.ENABLE_CARD_SCANNER.jsName())) {
                Fidel.enableCardScanner = optionsMap.getBoolean(FidelSetupKeys.Options.ENABLE_CARD_SCANNER.jsName());
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
            if (consentTextMap.hasKey(FidelSetupKeys.ConsentText.COMPANY_NAME.jsName())) {
                Fidel.companyName = consentTextMap.getString(FidelSetupKeys.ConsentText.COMPANY_NAME.jsName());
            }
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
