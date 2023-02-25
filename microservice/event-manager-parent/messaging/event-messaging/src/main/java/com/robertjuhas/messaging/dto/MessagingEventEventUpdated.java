package com.robertjuhas.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record MessagingEventEventUpdated(
        long eventID,
        String aggregateID,
        ZonedDateTime time,
        Long capacity,
        String place,
        String title,
        List<Long> users
) implements MessagingEvent {

}
