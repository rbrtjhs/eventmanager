package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.command.CreateEventCommandDTO;

import java.time.ZonedDateTime;

public record EventRootEntity(ZonedDateTime time, long capacity, String place, String title, String userID) {
    public EventRootEntity(CreateEventCommandDTO command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.userID());
    }
}
