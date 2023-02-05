package com.robertjuhas.ddd.event;

import com.robertjuhas.ddd.command.CreateEventCommand;
import ddd.event.AggregateEvent;

import java.time.ZonedDateTime;

public record AggregateEventEventCreated(
        ZonedDateTime time,
        long capacity,
        String place,
        String title,
        String userID
) implements AggregateEvent {

    public AggregateEventEventCreated(CreateEventCommand command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.userID());
    }
}
