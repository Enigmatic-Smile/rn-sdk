package com.fidelreactlibrary.adapters;

import com.fidelreactlibrary.fakes.ReadableMapStub;
import com.fidelapi.entities.CardVerificationConfiguration;
import static org.junit.Assert.*;

import org.junit.Test;

public class FidelVerificationConfigurationAdapterTest {

    private FidelVerificationConfigurationAdapter sut = new FidelVerificationConfigurationAdapter();

    @Test
    public void test_ShouldMapEmptyMapCorrectly() {
        CardVerificationConfiguration expectedConfig = new CardVerificationConfiguration("", "", null);

        CardVerificationConfiguration cardConfig = sut.adapt(ReadableMapStub.mapWithNoKey());

        assertEquals(expectedConfig, cardConfig);
    }

    @Test
    public void test_ShouldMapNonEmptyValidMapCorrectly() {
        CardVerificationConfiguration expectedConfig = new CardVerificationConfiguration("123", "456", "4567");

        CardVerificationConfiguration cardConfig = sut.adapt(ReadableMapStub.cardConfigWithAllValidConfigKeys());

        assertEquals(expectedConfig, cardConfig);
    }

    @Test
    public void test_ShouldMapNullIdCorrectly() {
        CardVerificationConfiguration cardConfig = sut.adapt(ReadableMapStub.cardConfigWithAllInValidConfigKeys());

        assertEquals("", cardConfig.id);
    }

    @Test
    public void test_ShouldMapNullConsentIdCorrectly() {
        CardVerificationConfiguration cardConfig = sut.adapt(ReadableMapStub.cardConfigWithAllInValidConfigKeys());

        assertEquals("", cardConfig.consentId);
    }

    @Test
    public void test_ShouldMapNullLast4DigitsCorrectly() {
        CardVerificationConfiguration cardConfig = sut.adapt(ReadableMapStub.cardConfigWithAllInValidConfigKeys());

        assertEquals(null, cardConfig.last4Digits);
    }
}
