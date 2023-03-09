package com.robertjuhas.dto.response;

import java.time.ZonedDateTime;

public record EventDTO(String id, String title, String place, ZonedDateTime time) {
}
