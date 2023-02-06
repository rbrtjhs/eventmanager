package com.robertjuhas.dto.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record MessagingEventEventUpdated(
        long eventID,
        String aggregateID,
        ZonedDateTime time,
        Long capacity,
        String place,
        String title
) implements MessagingEvent {

}
