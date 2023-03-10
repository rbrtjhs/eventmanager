package com.robertjuhas.route;

import com.robertjuhas.service.EventService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@EnableConfigurationProperties(value = EventService.class)
public class EventRoute {

    @Bean
    public RouteLocator eventRoutes(RouteLocatorBuilder routeLocatorBuilder, EventService eventService) {
        return routeLocatorBuilder.routes()
                .route("create", spec -> spec
                        .method(HttpMethod.POST, HttpMethod.PUT)
                        .and()
                        .path("/event")
                        .uri(eventService.getCommand().getUrl() + "/event"))
                .route("update", spec -> spec
                        .method(HttpMethod.GET)
                        .and()
                        .path("/event")
                        .uri(eventService.getCommand().getUrl() + "/event"))
                .route("subscribe", spec -> spec
                        .method(HttpMethod.POST)
                        .and()
                        .path("/event/subscribe")
                        .uri(eventService.getCommand().getUrl() + "/event/subscribe"))
                .route("unsubscribe", spec -> spec
                        .method(HttpMethod.POST)
                        .and()
                        .path("/event/unsubscribe")
                        .uri(eventService.getCommand().getUrl() + "/event/unsubscribe"))
                .build();
    }
}
