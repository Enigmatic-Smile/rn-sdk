package com.fidelreactlibrary.adapters;

import com.fidelreactlibrary.fakes.ReadableMapStub;
import com.fidelapi.entities.CardVerificationConfiguration;
import static org.junit.Assert.*;

import org.junit.Test;

public class FidelVerificationSetupAdapterTest {

    private FidelVerificationSetupAdapter sut = new FidelVerificationSetupAdapter();

    @Test
    public void test_ShouldMapEmptyMapCorrectly() {
        CardVerificationConfiguration cardConfig = sut.map(ReadableMapStub.mapWithNoKey());
        assertEquals("", cardConfig.id);
        assertEquals("", cardConfig.consentId);
        assertEquals("", cardConfig.last4Digits);
    }

    @Test
    public void test_ShouldMapNonEmptyValidMapCorrectly() {
        CardVerificationConfiguration cardConfig = sut.map(ReadableMapStub.mapWithVerificationSetup());
        assertEquals("123", cardConfig.id);
        assertEquals("456", cardConfig.consentId);
        assertEquals("4567", cardConfig.last4Digits);
    }
}
