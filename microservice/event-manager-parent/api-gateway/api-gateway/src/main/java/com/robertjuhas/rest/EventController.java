package com.robertjuhas.rest;

import com.robertjuhas.dto.command.event.CreateEventCommand;
import com.robertjuhas.dto.command.event.SubscribeToEventCommand;
import com.robertjuhas.dto.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.dto.request.CreateEventRequestDTO;
import com.robertjuhas.dto.request.SubscribeToEventRequestDTO;
import com.robertjuhas.dto.request.UnsubscribeFromEventRequestDTO;
import com.robertjuhas.dto.request.UpdateEventRequestDTO;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/event")
public class EventController {

    @Value("${event.command.service.uri}")
    private String eventCommandServiceURI;

    @PostMapping
    public Mono createEvent(@RequestBody CreateEventRequestDTO request) {
        var createEventCommand = new CreateEventCommand(request.time(), request.capacity(),
                request.place(), request.title(), request.createdBy());
        return WebClient
                .create()
                .post()
                .uri(eventCommandServiceURI + "/event")
                .bodyValue(createEventCommand)
                .accept(MediaType.TEXT_PLAIN)
                .acceptCharset(CharsetUtil.UTF_8)
                .retrieve()
                .bodyToMono(String.class);
    }

    @PutMapping
    public Mono updateEvent(@RequestBody UpdateEventRequestDTO request) {
        var updateCommand = new UpdateEventRequestDTO(request.aggregateID(), request.time(),
                request.capacity(), request.place(), request.title());
        return WebClient.create()
                .put()
                .uri(eventCommandServiceURI + "/event")
                .bodyValue(updateCommand)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }

    @PostMapping("/subscribe")
    public Mono subscribeToEvent(@RequestBody SubscribeToEventRequestDTO request) {
        var subscribeCommand = new SubscribeToEventCommand(request.aggregateID(), request.userID());
        return WebClient.create()
                .post()
                .uri(eventCommandServiceURI + "/event/subscribe")
                .bodyValue(subscribeCommand)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }

    @PostMapping("/unsubscribe")
    public Mono unsubscribeFromEvent(@RequestBody UnsubscribeFromEventRequestDTO request) {
        var unsubscribeCommand = new UnsubscribeFromEventCommand(request.aggregateID(), request.userID());
        return WebClient.create()
                .post()
                .uri(eventCommandServiceURI + "/event/unsubscribe")
                .bodyValue(unsubscribeCommand)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }
}
