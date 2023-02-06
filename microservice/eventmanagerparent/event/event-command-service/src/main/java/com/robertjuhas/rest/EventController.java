package com.robertjuhas.rest;

import com.robertjuhas.rest.dto.request.CreateEventRequestDTO;
import com.robertjuhas.rest.dto.request.SubscribeToEventRequestDTO;
import com.robertjuhas.rest.dto.request.UnsubscribeFromEventRequestDTO;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;
import com.robertjuhas.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public record EventController(EventService eventService) {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody CreateEventRequestDTO request) {
        String aggregateID = eventService.createEvent(request);
        return ResponseEntity.ok(aggregateID);
    }

    @PutMapping
    public ResponseEntity updateEvent(@RequestBody UpdateEventRequestDTO request) {
        eventService.updateEvent(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe")
    public ResponseEntity subscribeToEvent(@RequestBody SubscribeToEventRequestDTO request) {
        eventService.subscribeToEvent(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity unsubscribeFromEvent(@RequestBody UnsubscribeFromEventRequestDTO request) {
        eventService.unsubscribeFromEvent(request);
        return ResponseEntity.ok().build();
    }
}
