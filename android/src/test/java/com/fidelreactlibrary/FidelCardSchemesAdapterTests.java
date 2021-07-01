package com.fidelreactlibrary;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReadableArray;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;

import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

    //Test to expose correct card scheme values
    @Test
    public void test_ToExposeCorrectVisaValue() {
        int cardSchemesValue = getConstantKeyValues().get(VISA_CARD_SCHEME_KEY);
        assertEquals(Fidel.CardScheme.VISA.ordinal(), cardSchemesValue);
    }

    @Test
    public void test_ToExposeCorrectMastercardValue() {
        int cardSchemesValue = getConstantKeyValues().get(MASTERCARD_SCHEME_KEY);
        assertEquals(Fidel.CardScheme.MASTERCARD.ordinal(), cardSchemesValue);
    }

    @Test
    public void test_ToExposeCorrectAmericanExpressValue() {
        int cardSchemesValue = getConstantKeyValues().get(AMEX_SCHEME_KEY);
        assertEquals(Fidel.CardScheme.AMERICAN_EXPRESS.ordinal(), cardSchemesValue);
    }

    //Adaptation tests
    @Test
    public void test_WhenAdaptingArrayWithVisa_ReturnSetWithVisaCardScheme() {
        assertCorrectConversionWithSchemes(Fidel.CardScheme.VISA);
    }

    @Test
    public void test_WhenAdaptingArrayWithMastercard_ReturnSetWithMastercardCardScheme() {
        assertCorrectConversionWithSchemes(Fidel.CardScheme.MASTERCARD);
    }

    @Test
    public void test_WhenAdaptingArrayWithAmericanExpress_ReturnSetWithAmericanExpressCardScheme() {
        assertCorrectConversionWithSchemes(Fidel.CardScheme.AMERICAN_EXPRESS);
    }

    @Test
    public void test_WhenAdaptingArrayWith2Schemes_ReturnSetWith2Schemes() {
        Fidel.CardScheme[] expectedSchemes = {
                Fidel.CardScheme.VISA,
                Fidel.CardScheme.AMERICAN_EXPRESS
        };
        assertCorrectConversionWithSchemes(expectedSchemes);
    }

    @Test
    public void test_WhenAdaptingArrayWith3Schemes_ReturnSetWith2Schemes() {
        Fidel.CardScheme[] expectedSchemes = {
                Fidel.CardScheme.VISA,
                Fidel.CardScheme.MASTERCARD,
                Fidel.CardScheme.AMERICAN_EXPRESS
        };
        assertCorrectConversionWithSchemes(expectedSchemes);
    }

    @Test
    public void test_WhenAdapting0Schemes_ReturnEmptySet() {
        Set<Fidel.CardScheme> result = sut.cardSchemesWithReadableArray(new JavaOnlyArray());
        assertEquals(0, result.size());
    }

    @Test
    public void test_WhenAdaptingNullSchemes_ReturnNullSet() {
        assertNull(sut.cardSchemesWithReadableArray(null));
    }

    @Test
    public void test_WhenAdaptingSchemeListWithInvalidValues_IgnoreTheInvalidValues() {
        float invalidValue = (float) Fidel.CardScheme.values().length + 10;
        ReadableArray invalidDoubleValues = JavaOnlyArray.of(invalidValue, Fidel.CardScheme.VISA.ordinal());
        Set<Fidel.CardScheme> result = sut.cardSchemesWithReadableArray(invalidDoubleValues);
        assertEquals(EnumSet.of(Fidel.CardScheme.VISA), result);
    }

    @Test
    public void test_WhenAdaptingSchemeListWithValidFloatValue_AdaptValidFloatValue() {
        double validDoubleValue = (double) Fidel.CardScheme.MASTERCARD.ordinal();
        ReadableArray invalidDoubleValues = JavaOnlyArray.of(validDoubleValue, Fidel.CardScheme.VISA.ordinal());
        Set<Fidel.CardScheme> result = sut.cardSchemesWithReadableArray(invalidDoubleValues);
        assertEquals(EnumSet.of(Fidel.CardScheme.VISA, Fidel.CardScheme.MASTERCARD), result);
    }

    //Helpers
    private @Nonnull Map<String, Integer> getConstantKeyValues() {
        Map<String, Object> exposedConstants = sut.getConstants();
        Map<String, Integer> constantKeyValues = (Map<String, Integer>)exposedConstants.get(CARD_SCHEMES_KEY);
        if (constantKeyValues == null) return new HashMap<>();
        return constantKeyValues;
    }

    private void assertCorrectConversionWithSchemes(Fidel.CardScheme... schemes) {
        Integer[] schemeOrdinals = new Integer[schemes.length];
        int schemeNumber = 0;
        for (Fidel.CardScheme scheme :
                schemes) {
            schemeOrdinals[schemeNumber] = scheme.ordinal();
            schemeNumber++;
        }
        ReadableArray arrayStub = JavaOnlyArray.of((Object[]) schemeOrdinals);
        Set<Fidel.CardScheme> result = sut.cardSchemesWithReadableArray(arrayStub);
        assertEquals(EnumSet.copyOf(Arrays.asList(schemes)), result);
    }
}
