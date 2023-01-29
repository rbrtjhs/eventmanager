package com.robertjuhas.rest;

import com.robertjuhas.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @GetMapping
    public ResponseEntity getEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity getEventsByOrganization(@PathVariable(name = "id") String userID) {
        return ResponseEntity.ok(eventService.findByUserID(userID));
    }
}
