package com.robertjuhas.dto.command.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record UnsubscribeFromEventCommand(@NotBlank String aggregateID, @Positive long userID) {
}
