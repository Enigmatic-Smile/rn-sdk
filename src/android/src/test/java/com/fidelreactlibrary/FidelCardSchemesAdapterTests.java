package com.fidelreactlibrary;

import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.FidelCardSchemesAdapter;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public final class FidelCardSchemesAdapterTests {
    private static final String CARD_SCHEMES_KEY = "CardScheme";
    private static final String VISA_CARD_SCHEME_KEY = "visa";
    private static final String MASTERCARD_SCHEME_KEY = "mastercard";
    private static final String AMEX_SCHEME_KEY = "americanExpress";

    private FidelCardSchemesAdapter sut = new FidelCardSchemesAdapter();

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

    //Helpers
    private @NotNull Map<String, Integer> getConstantKeyValues() {
        Map<String, Object> exposedConstants = sut.getConstants();
        Map<String, Integer> constantKeyValues = (Map<String, Integer>)exposedConstants.get(CARD_SCHEMES_KEY);
        if (constantKeyValues == null) return new HashMap<>();
        return constantKeyValues;
    }
}
