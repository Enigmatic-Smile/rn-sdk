package com.fidelreactlibrary.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BridgeLibraryEventTypesTests {
    @Test
    public void test_ResultAvailableEventType_ShouldHaveCorrectEventName() {
        assertEquals("ResultAvailable", BridgeLibraryEventTypes.RESULT_AVAILABLE.getEventName());
    }

    @Test
    public void test_CardVerificationStartedEventType_ShouldHaveCorrectEventName() {
        assertEquals("CardVerificationStarted", BridgeLibraryEventTypes.CARD_VERIFICATION_STARTED.getEventName());
    }
}
