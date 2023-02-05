package com.robertjuhas.ddd.command;

import com.robertjuhas.dto.rest.request.CreateEventRequestDTO;

import java.time.ZonedDateTime;

public record CreateEventCommand(ZonedDateTime time, long capacity, String place, String title, String userID) implements EventCommand {

    public CreateEventCommand(CreateEventRequestDTO createEventRequestDTO, String userID) {
        this(createEventRequestDTO.time(), createEventRequestDTO.capacity(), createEventRequestDTO.place(), createEventRequestDTO.title(), userID);
    }
}
