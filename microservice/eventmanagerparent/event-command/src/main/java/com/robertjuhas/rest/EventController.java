package com.robertjuhas.rest;

import com.robertjuhas.dto.rest.request.CreateEventRequestDTO;
import com.robertjuhas.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public record EventController(EventService eventService) {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody CreateEventRequestDTO createEventRequestDTO) {
        eventService.createEvent(createEventRequestDTO);
        return ResponseEntity.ok().build();
    }
}
