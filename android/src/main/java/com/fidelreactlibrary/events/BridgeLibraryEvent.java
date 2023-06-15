package com.fidelreactlibrary.events;

public enum BridgeLibraryEvent {
    RESULT_AVAILABLE("ResultAvailable"),
    CARD_VERIFICATION_STARTED("CardVerificationStarted"),
    CARD_VERIFICATION_CHOICE("CardVerificationChoiceSelected");

    private final String eventName;

    BridgeLibraryEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }
}
