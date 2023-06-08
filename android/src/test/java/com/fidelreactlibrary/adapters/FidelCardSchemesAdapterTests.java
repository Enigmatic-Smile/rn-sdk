package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.fidelapi.entities.CardScheme;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;

import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

import androidx.annotation.NonNull;

public final class FidelCardSchemesAdapterTests {
    private static final String CARD_SCHEMES_KEY = "CardScheme";
    private static final String VISA_CARD_SCHEME_KEY = "visa";
    private static final String MASTERCARD_SCHEME_KEY = "mastercard";
    private static final String AMEX_SCHEME_KEY = "americanExpress";

    private final FidelCardSchemesAdapter sut = new FidelCardSchemesAdapter();

    //Test to expose correct card scheme keys
    @Test
    public void test_ToExposeConstantsWithCorrectKey() {
        Map<String, Object> exposedConstants = sut.getConstants();
        assertThat(exposedConstants.keySet(), hasItem(CARD_SCHEMES_KEY));
    }

    @Test
    public void test_ToExposeCorrectVisaKey() {
        assertThat(getConstantKeyValues().keySet(), hasItem(VISA_CARD_SCHEME_KEY));
    }

    @Test
    public void test_ToExposeCorrectAmericanExpressKey() {
        assertThat(getConstantKeyValues().keySet(), hasItem(AMEX_SCHEME_KEY));
    }

    @Test
    public void test_ToExposeCorrectMastercardKey() {
        assertThat(getConstantKeyValues().keySet(), hasItem(MASTERCARD_SCHEME_KEY));
    }

    @Test
    public void test_ToAdaptVisaCardSchemeWithCorrectJSValue() {
        assertEquals("visa", sut.jsValue(CardScheme.VISA));
    }

    @Test
    public void test_ToAdaptMastercardCardSchemeWithCorrectJSValue() {
        assertEquals("mastercard", sut.jsValue(CardScheme.MASTERCARD));
    }

    @Test
    public void test_ToAdaptAmexCardSchemeWithCorrectJSValue() {
        assertEquals("americanExpress", sut.jsValue(CardScheme.AMERICAN_EXPRESS));
    }

    //Test to expose correct card scheme values
    @Test
    public void test_ToExposeCorrectVisaValue() {
        String cardSchemesValue = getConstantKeyValues().get(VISA_CARD_SCHEME_KEY);
        assertEquals("visa", cardSchemesValue);
    }

    @Test
    public void test_ToExposeCorrectMastercardValue() {
        String cardSchemesValue = getConstantKeyValues().get(MASTERCARD_SCHEME_KEY);
        assertEquals("mastercard", cardSchemesValue);
    }

    @Test
    public void test_ToExposeCorrectAmericanExpressValue() {
        String cardSchemesValue = getConstantKeyValues().get(AMEX_SCHEME_KEY);
        assertEquals("americanExpress", cardSchemesValue);
    }

    //Adaptation tests
    @Test
    public void test_WhenAdaptingArrayWithVisa_ReturnSetWithVisaCardScheme() {
        assertCorrectConversionWithSchemes(CardScheme.VISA);
    }

    @Test
    public void test_WhenAdaptingArrayWithMastercard_ReturnSetWithMastercardCardScheme() {
        assertCorrectConversionWithSchemes(CardScheme.MASTERCARD);
    }

    @Test
    public void test_WhenAdaptingArrayWithAmericanExpress_ReturnSetWithAmericanExpressCardScheme() {
        assertCorrectConversionWithSchemes(CardScheme.AMERICAN_EXPRESS);
    }

    @Test
    public void test_WhenAdaptingArrayWith2Schemes_ReturnSetWith2Schemes() {
        CardScheme[] expectedSchemes = {
                CardScheme.VISA,
                CardScheme.AMERICAN_EXPRESS
        };
        assertCorrectConversionWithSchemes(expectedSchemes);
    }

    @Test
    public void test_WhenAdaptingArrayWith3Schemes_ReturnSetWith2Schemes() {
        CardScheme[] expectedSchemes = {
                CardScheme.VISA,
                CardScheme.MASTERCARD,
                CardScheme.AMERICAN_EXPRESS
        };
        assertCorrectConversionWithSchemes(expectedSchemes);
    }

    @Test
    public void test_WhenAdapting0Schemes_ReturnEmptySet() {
        Set<CardScheme> result = sut.cardSchemesWithReadableArray(new JavaOnlyArray());
        assertEquals(0, result.size());
    }

    @Test
    public void test_WhenAdaptingNullSchemes_ReturnEmptySet() {
        assertTrue(sut.cardSchemesWithReadableArray(null).isEmpty());
    }

    @Test
    public void test_WhenAdaptingSchemeListWithInvalidValues_IgnoreTheInvalidValues() {
        float invalidValue = (float) CardScheme.values().length + 10;
        ReadableArray invalidDoubleValues = JavaOnlyArray.of(invalidValue, sut.jsValue(CardScheme.VISA));
        Set<CardScheme> result = sut.cardSchemesWithReadableArray(invalidDoubleValues);
        assertEquals(EnumSet.of(CardScheme.VISA), result);
    }

    //Helpers
    private @NonNull Map<String, String> getConstantKeyValues() {
        Map<String, Object> exposedConstants = sut.getConstants();
        Map<String, String> constantKeyValues = (Map<String, String>)exposedConstants.get(CARD_SCHEMES_KEY);
        if (constantKeyValues == null) return new HashMap<>();
        return constantKeyValues;
    }

    private void assertCorrectConversionWithSchemes(CardScheme... schemes) {
        String[] schemeJsNames = new String[schemes.length];
        int schemeNumber = 0;
        for (CardScheme scheme : schemes) {
            schemeJsNames[schemeNumber] = sut.jsValue(scheme);
            schemeNumber++;
        }
        ReadableArray arrayStub = JavaOnlyArray.of((Object[]) schemeJsNames);
        Set<CardScheme> result = sut.cardSchemesWithReadableArray(arrayStub);
        assertEquals(EnumSet.copyOf(Arrays.asList(schemes)), result);
    }
}
