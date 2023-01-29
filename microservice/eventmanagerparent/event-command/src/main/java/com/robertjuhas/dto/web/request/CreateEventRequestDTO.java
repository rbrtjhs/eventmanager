package com.robertjuhas.dto.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public record CreateEventRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        ZonedDateTime time,
        long capacity,
        String place,
        String title) {
}
