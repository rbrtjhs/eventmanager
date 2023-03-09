package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public record SubscribeToEventCommand(@NotBlank String aggregateID,
                                      @Positive long userID) implements EventCommand {
}
