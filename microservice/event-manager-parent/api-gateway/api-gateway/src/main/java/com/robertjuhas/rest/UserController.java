package com.robertjuhas.rest;

import com.robertjuhas.dto.request.CreateUserRequestDTO;
import com.robertjuhas.dto.request.SubscribeRequestDTO;
import com.robertjuhas.dto.response.EventServiceUserEventsDTO;
import com.robertjuhas.dto.response.UserAndEventsDTO;
import com.robertjuhas.dto.response.UserServiceUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${user.service.uri}")
    private String userServiceURI;

    @Value("${event.query.service.uri}")
    private String eventQueryServiceURI;

    @GetMapping("/{id}")
    public Mono<UserAndEventsDTO> getUser(@PathVariable("id") Long id) {
        var userService = WebClient
                .create()
                .get()
                .uri(userServiceURI + "/user/{id}", id)
                .retrieve()
                .bodyToMono(UserServiceUserDTO.class);
        var eventService = WebClient
                .create()
                .get()
                .uri(eventQueryServiceURI + "/{id}", id)
                .retrieve()
                .bodyToMono(EventServiceUserEventsDTO.class);
        return Mono.zip(userService, eventService).map(x -> new UserAndEventsDTO(x.getT1().username(), x.getT2().events()));
    }

    @PostMapping
    public Mono register(@RequestBody CreateUserRequestDTO request) {
        return WebClient.create().post()
                .uri(userServiceURI + "/user")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }

    @PostMapping("/subscribe")
    public Mono subscribeToUser(@RequestBody SubscribeRequestDTO request) {
        return WebClient.create().post()
                .uri(userServiceURI + "/user/subscribe")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }
}
