package com.robertjuhas.dto.messaging;

import java.time.ZonedDateTime;

public record MessagingEventEventUpdated(
        long eventID,
        String aggregateID,
        ZonedDateTime time,
        long capacity,
        String place,
        String title
) implements MessagingEvent {

}
