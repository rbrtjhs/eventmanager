package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.command.CreateEventCommand;

import java.time.ZonedDateTime;

public record EventRootEntity(ZonedDateTime time, long capacity, String place, String title, String userID) {
    public EventRootEntity(CreateEventCommand command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.userID());
    }
}
