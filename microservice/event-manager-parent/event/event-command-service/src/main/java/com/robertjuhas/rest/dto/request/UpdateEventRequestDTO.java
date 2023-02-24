package com.robertjuhas.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

public record UpdateEventRequestDTO(
        @NotEmpty
        String aggregateID,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        ZonedDateTime time,
        @Positive
        long capacity,
        @NotEmpty
        String place,
        @NotEmpty
        String title) {
}
