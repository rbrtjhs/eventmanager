package com.robertjuhas.ddd.event;

import com.robertjuhas.ddd.command.CreateEventCommandDTO;
import ddd.event.AggregateEvent;

import java.time.ZonedDateTime;

public record EventCreatedEvent(
        ZonedDateTime time,
        long capacity,
        String place,
        String title,
        String userID
) implements AggregateEvent {

    public EventCreatedEvent(CreateEventCommandDTO command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.userID());
    }
}
