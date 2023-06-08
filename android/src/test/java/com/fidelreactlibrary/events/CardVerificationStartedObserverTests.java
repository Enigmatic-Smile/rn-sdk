package com.fidelreactlibrary.events;

import static org.junit.Assert.assertEquals;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.entities.ConsentDetails;
import com.fidelreactlibrary.fakes.DataProcessorSpy;

import org.junit.Before;
import org.junit.Test;

public class CardVerificationStartedObserverTests {
    private DataProcessorSpy<ReadableMap> adaptedConsentDetailsProcessorSpy;
    private CardVerificationStartedObserver observer;

    @Before
    public void setup() {
        adaptedConsentDetailsProcessorSpy = new DataProcessorSpy<>();
        observer = new CardVerificationStartedObserver(
                adaptedConsentDetailsProcessorSpy,
                JavaOnlyMap::new
        );
    }

    @Test
    public void test_WhenCardVerificationStarted_ShouldForwardMapWithCorrectCardID() {
        String testCardId = "some card ID";
        ConsentDetails consentDetailsStub = new ConsentDetails(testCardId, "", "");
        observer.onCardVerificationStarted(consentDetailsStub);
        assertEquals(testCardId, adaptedConsentDetailsProcessorSpy.dataToProcess.getString("cardId"));
    }

    @Test
    public void test_WhenCardVerificationStarted_ShouldForwardMapWithCorrectConsentID() {
        String testConsentId = "some consent ID";
        ConsentDetails consentDetailsStub = new ConsentDetails("", testConsentId, "");
        observer.onCardVerificationStarted(consentDetailsStub);
        assertEquals(testConsentId, adaptedConsentDetailsProcessorSpy.dataToProcess.getString("consentId"));
    }

    @Test
    public void test_WhenCardVerificationStarted_ShouldForwardMapWithCorrectProgramID() {
        String testProgramId = "some program ID";
        ConsentDetails consentDetailsStub = new ConsentDetails("", "", testProgramId);
        observer.onCardVerificationStarted(consentDetailsStub);
        assertEquals(testProgramId, adaptedConsentDetailsProcessorSpy.dataToProcess.getString("programId"));
    }
}
