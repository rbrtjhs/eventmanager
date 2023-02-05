package com.robertjuhas.ddd.command;

import com.robertjuhas.dto.rest.request.CreateEventRequestDTO;

import java.time.ZonedDateTime;

public record CreateEventCommandDTO(ZonedDateTime time, long capacity, String place, String title, String userID) implements EventCommand {

    public CreateEventCommandDTO(CreateEventRequestDTO createEventRequestDTO, String userID) {
        this(createEventRequestDTO.time(), createEventRequestDTO.capacity(), createEventRequestDTO.place(), createEventRequestDTO.title(), userID);
    }
}
