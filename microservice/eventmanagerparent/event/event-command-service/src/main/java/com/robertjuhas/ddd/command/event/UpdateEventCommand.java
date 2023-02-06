package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;

import java.time.ZonedDateTime;

public record UpdateEventCommand(ZonedDateTime time, long capacity, String place, String title) implements EventCommand {

    public UpdateEventCommand(UpdateEventRequestDTO createEventRequestDTO) {
        this(createEventRequestDTO.time(), createEventRequestDTO.capacity(), createEventRequestDTO.place(), createEventRequestDTO.title());
    }
}
