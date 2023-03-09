package com.robertjuhas.rest;

import com.robertjuhas.ddd.command.event.CreateEventCommand;
import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.ddd.command.event.UpdateEventCommand;
import com.robertjuhas.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public record
EventController(EventService eventService) {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody CreateEventCommand command) {
        String aggregateID = eventService.createEvent(command);
        return ResponseEntity.ok(aggregateID);
    }

    @PutMapping
    public ResponseEntity updateEvent(@RequestBody UpdateEventCommand command) {
        eventService.updateEvent(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe")
    public ResponseEntity subscribeToEvent(@RequestBody SubscribeToEventCommand command) {
        eventService.subscribeToEvent(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity unsubscribeFromEvent(@RequestBody UnsubscribeFromEventCommand command) {
        eventService.unsubscribeFromEvent(command);
        return ResponseEntity.ok().build();
    }
}
