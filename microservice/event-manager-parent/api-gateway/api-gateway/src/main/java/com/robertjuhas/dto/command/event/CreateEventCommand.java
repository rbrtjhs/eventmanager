package com.robertjuhas.dto.command.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

public record CreateEventCommand(@NotNull ZonedDateTime time, @Positive long capacity, @NotBlank String place,
                                 @NotBlank String title, @Positive long createdBy) {
}
