package com.robertjuhas.dto.ddd.event;

import com.robertjuhas.dto.ddd.command.CreateEventCommandDTO;
import ddd.Event;

import java.time.ZonedDateTime;

public record EventCreatedEvent(
        ZonedDateTime time,
        long capacity,
        String place,
        String title,
        String userID
) implements Event {

    public EventCreatedEvent(CreateEventCommandDTO command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.userID());
    }
}
