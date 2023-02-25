package com.robertjuhas.messaging.dto;

import java.time.ZonedDateTime;

public record MessagingEventEventCreated(long eventID,
                                         String aggregateID,
                                         ZonedDateTime time,
                                         long capacity,
                                         String place,
                                         String title,
                                         long userID) implements MessagingEvent {
}
