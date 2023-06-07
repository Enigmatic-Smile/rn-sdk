package com.fidelreactlibrary.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BridgeLibraryEventTests {
    @Test
    public void test_ResultAvailableEventType_ShouldHaveCorrectEventName() {
        assertEquals("ResultAvailable", BridgeLibraryEvent.RESULT_AVAILABLE.getEventName());
    }

    @Test
    public void test_CardVerificationStartedEventType_ShouldHaveCorrectEventName() {
        assertEquals("CardVerificationStarted", BridgeLibraryEvent.CARD_VERIFICATION_STARTED.getEventName());
    }
}
