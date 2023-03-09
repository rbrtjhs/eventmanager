package com.robertjuhas.dto.command.event;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

public record UpdateEventCommand(@NotBlank String aggregateID, ZonedDateTime time, long capacity,
                                 String place, String title) {

}
