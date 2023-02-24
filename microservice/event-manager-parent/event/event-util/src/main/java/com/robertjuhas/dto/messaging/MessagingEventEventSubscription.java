package com.robertjuhas.dto.messaging;

public record MessagingEventEventSubscription(long eventID, String aggregateID, long value) implements MessagingEvent {
}
