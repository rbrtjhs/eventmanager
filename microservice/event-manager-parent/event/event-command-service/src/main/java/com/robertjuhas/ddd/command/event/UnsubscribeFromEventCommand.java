package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record UnsubscribeFromEventCommand(@NotBlank String aggregateID, @Positive long userID) implements EventCommand {
}
