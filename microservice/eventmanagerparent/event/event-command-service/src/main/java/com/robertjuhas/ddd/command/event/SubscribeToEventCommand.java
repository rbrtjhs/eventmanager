package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;

import java.time.ZonedDateTime;

public record SubscribeToEventCommand(
        long userID,
        ZonedDateTime time) implements EventCommand {
    public SubscribeToEventCommand(long userID) {
        this(userID, ZonedDateTime.now());
    }
}
