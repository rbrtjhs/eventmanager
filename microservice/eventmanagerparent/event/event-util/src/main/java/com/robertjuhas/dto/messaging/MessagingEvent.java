package com.robertjuhas.dto.messaging;

public interface MessagingEvent {
    long eventID();
    String aggregateID();
}
