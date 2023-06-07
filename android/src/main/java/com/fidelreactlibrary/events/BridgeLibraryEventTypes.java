package com.fidelreactlibrary.events;

public enum BridgeLibraryEventTypes {
    RESULT_AVAILABLE("ResultAvailable"),
    CARD_VERIFICATION_STARTED("CardVerificationStarted");

    private final String eventName;

    BridgeLibraryEventTypes(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }
}
