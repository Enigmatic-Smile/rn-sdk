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

        CardVerificationConfiguration cardVerificationConfig = sut.adapt(ReadableMapStub.mapWithNoKey());

        assertEquals(expectedConfig, cardVerificationConfig);
    }

    @Test
    public void test_ShouldMapNonEmptyValidMapCorrectly() {
        CardVerificationConfiguration expectedConfig = new CardVerificationConfiguration("123", "456", "4567");

        CardVerificationConfiguration cardVerificationConfig = sut.adapt(ReadableMapStub.cardConfigWithAllValidConfigKeys());

        assertEquals(expectedConfig, cardVerificationConfig);
    }

    @Test
    public void test_ShouldMapNullIdCorrectly() {
        CardVerificationConfiguration cardVerificationConfig = sut.adapt(ReadableMapStub.cardConfigWithAllInValidConfigKeys());

        assertEquals("", cardVerificationConfig.id);
    }

    @Test
    public void test_ShouldMapNullConsentIdCorrectly() {
        CardVerificationConfiguration cardVerificationConfig = sut.adapt(ReadableMapStub.cardConfigWithAllInValidConfigKeys());

        assertEquals("", cardVerificationConfig.consentId);
    }

    @Test
    public void test_ShouldMapNullLast4DigitsCorrectly() {
        CardVerificationConfiguration cardVerificationConfig = sut.adapt(ReadableMapStub.cardConfigWithAllInValidConfigKeys());

        assertNull(cardVerificationConfig.last4Digits);
    }
}
