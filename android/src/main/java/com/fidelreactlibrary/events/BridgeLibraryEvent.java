package com.fidelreactlibrary.events;

public enum BridgeLibraryEvent {
    RESULT_AVAILABLE("ResultAvailable"),

    private final String eventName;

    BridgeLibraryEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }
}
