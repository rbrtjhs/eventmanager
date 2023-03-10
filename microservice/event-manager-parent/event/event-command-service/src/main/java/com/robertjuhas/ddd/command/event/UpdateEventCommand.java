package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

public record UpdateEventCommand(@NotBlank String aggregateID, ZonedDateTime time, long capacity,
                                 String place, String title) implements EventCommand {

}
