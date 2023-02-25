package com.robertjuhas.rest;

import com.robertjuhas.ddd.command.event.CreateEventCommand;
import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.ddd.command.event.UpdateEventCommand;
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
        long userID = 1L;
        var command = new CreateEventCommand(request, userID);
        String aggregateID = eventService.createEvent(command);
        return ResponseEntity.ok(aggregateID);
    }

    @PutMapping
    public ResponseEntity updateEvent(@RequestBody UpdateEventRequestDTO request) {
        eventService.updateEvent(new UpdateEventCommand(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe")
    public ResponseEntity subscribeToEvent(@RequestBody SubscribeToEventRequestDTO request) {
        long userID = 1L;
        eventService.subscribeToEvent(new SubscribeToEventCommand(request, userID));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity unsubscribeFromEvent(@RequestBody UnsubscribeFromEventRequestDTO request) {
        long userID = 1L;
        eventService.unsubscribeFromEvent(new UnsubscribeFromEventCommand(request.aggregateID(), userID));
        return ResponseEntity.ok().build();
    }
}
