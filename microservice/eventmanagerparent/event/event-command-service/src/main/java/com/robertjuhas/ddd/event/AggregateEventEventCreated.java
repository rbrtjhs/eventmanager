package com.robertjuhas.ddd.event;

import com.robertjuhas.ddd.command.CreateEventCommand;

import java.time.ZonedDateTime;

public record AggregateEventEventCreated(
        ZonedDateTime time,
        long capacity,
        String place,
        String title,
        long userID
) implements AggregateEventEvent {

    public AggregateEventEventCreated(CreateEventCommand command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.createdBy());
    }
}
