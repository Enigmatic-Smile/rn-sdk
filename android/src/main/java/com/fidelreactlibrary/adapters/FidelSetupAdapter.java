package com.fidelreactlibrary.adapters;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelreactlibrary.adapters.abstraction.CardSchemesAdapter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.CountryAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

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
        return constants;
    }
}
