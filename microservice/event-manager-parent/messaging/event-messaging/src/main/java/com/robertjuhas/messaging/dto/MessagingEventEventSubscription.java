package com.robertjuhas.messaging.dto;

public record MessagingEventEventSubscription(long eventID, String aggregateID, long value) implements MessagingEvent {
}
