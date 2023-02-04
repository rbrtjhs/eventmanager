package com.robertjuhas.dto.messaging;

import java.time.ZonedDateTime;

public record MessagingEventEventCreated(String eventID,
                                         String aggregateID,
                                         ZonedDateTime time,
                                         long capacity,
                                         String place,
                                         String title,
                                         String userID) implements MessagingEvent {
}
