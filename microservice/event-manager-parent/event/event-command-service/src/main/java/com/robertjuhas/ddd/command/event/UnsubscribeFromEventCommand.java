package com.robertjuhas.ddd.command.event;

import com.robertjuhas.rest.dto.request.UnsubscribeFromEventRequestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record UnsubscribeFromEventCommand(@NotBlank String aggregateID, @Positive long userID) {
    public UnsubscribeFromEventCommand(@NotNull UnsubscribeFromEventRequestDTO requestDTO) {
        this(requestDTO.aggregateID(), requestDTO.userID());
    }
}
