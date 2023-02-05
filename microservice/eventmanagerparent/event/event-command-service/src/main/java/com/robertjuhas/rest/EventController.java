package com.robertjuhas.rest;

import com.robertjuhas.rest.dto.request.CreateEventRequestDTO;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;
import com.robertjuhas.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public record EventController(EventService eventService) {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody CreateEventRequestDTO createEventRequestDTO) {
        eventService.createEvent(createEventRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateEvent(@RequestBody UpdateEventRequestDTO updateEventRequestDTO) {
        eventService.updateEvent(updateEventRequestDTO);
        return ResponseEntity.ok().build();
    }
}
