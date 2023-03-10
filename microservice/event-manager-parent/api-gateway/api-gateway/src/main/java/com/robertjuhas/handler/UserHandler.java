package com.robertjuhas.handler;

import com.robertjuhas.dto.response.EventDTO;
import com.robertjuhas.dto.response.UserAndEventsDTO;
import com.robertjuhas.dto.response.UserServiceUserDTO;
import com.robertjuhas.service.EventService;
import com.robertjuhas.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
public class UserHandler {
    private UserService userService;
    private EventService eventService;

    public Mono<ServerResponse> getUserAndEventsDTO(ServerRequest serverRequest) {
        var userID = Long.valueOf(serverRequest.pathVariable("id"));
        var user = WebClient
                .create()
                .get()
                .uri(userService.getUrl() + "/user/{id}", userID)
                .retrieve()
                .bodyToMono(UserServiceUserDTO.class);
        var usersEvents = WebClient
                .create()
                .get()
                .uri(eventService.getQuery().getUrl() + "/event/user/{id}", userID)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EventDTO>>() {
                });
        var combined = Mono.zip(user, usersEvents).map(x -> new UserAndEventsDTO(x.getT1().username(), x.getT2()));
        return combined.flatMap(x -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(x));
    }
}
