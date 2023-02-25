package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public record UpdateEventCommand(@NotBlank String aggregateID, ZonedDateTime time, long capacity,
                                 String place, String title) implements EventCommand {

    public UpdateEventCommand(@NotNull UpdateEventRequestDTO createEventRequestDTO) {
        this(createEventRequestDTO.aggregateID(), createEventRequestDTO.time(), createEventRequestDTO.capacity(), createEventRequestDTO.place(), createEventRequestDTO.title());
    }
}
