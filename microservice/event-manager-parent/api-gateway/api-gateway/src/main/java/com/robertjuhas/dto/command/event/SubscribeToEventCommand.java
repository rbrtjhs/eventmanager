package com.robertjuhas.dto.command.event;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public record SubscribeToEventCommand(
        @NotEmpty String aggregateID,
        @Positive long userID) {
}
