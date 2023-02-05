package com.robertjuhas.ddd.command;

import com.robertjuhas.rest.dto.request.CreateEventRequestDTO;

import java.time.ZonedDateTime;

public record CreateEventCommand(ZonedDateTime time, long capacity, String place, String title, long createdBy) implements EventCommand {

    public CreateEventCommand(CreateEventRequestDTO createEventRequestDTO, long createdBy) {
        this(createEventRequestDTO.time(), createEventRequestDTO.capacity(), createEventRequestDTO.place(), createEventRequestDTO.title(), createdBy);
    }
}
