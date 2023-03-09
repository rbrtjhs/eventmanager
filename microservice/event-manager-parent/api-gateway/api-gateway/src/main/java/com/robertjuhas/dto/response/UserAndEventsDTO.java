package com.robertjuhas.dto.response;

import java.util.List;

public record UserAndEventsDTO(String username, List<EventDTO> events) {
}
