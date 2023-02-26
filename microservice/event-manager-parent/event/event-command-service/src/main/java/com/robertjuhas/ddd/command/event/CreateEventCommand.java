package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;
import com.robertjuhas.rest.dto.request.CreateEventRequestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

public record CreateEventCommand(@NotNull ZonedDateTime time, @Positive long capacity, @NotBlank String place,
                                 @NotBlank String title, @Positive long createdBy) implements EventCommand {

    public CreateEventCommand(@NotNull CreateEventRequestDTO createEventRequestDTO) {
        this(createEventRequestDTO.time(), createEventRequestDTO.capacity(), createEventRequestDTO.place(), createEventRequestDTO.title(), createEventRequestDTO.createdBy());
    }
}
