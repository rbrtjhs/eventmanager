package com.robertjuhas.route;

import com.robertjuhas.handler.UserHandler;
import com.robertjuhas.service.EventService;
import com.robertjuhas.service.UserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableConfigurationProperties(value = UserService.class)
public class UserRoute {

    @Bean
    public RouteLocator userRoutes(RouteLocatorBuilder builder, UserService userService) {
        return builder.routes()
                .route("create-user", spec -> spec.method(HttpMethod.POST).and().path("/user").uri(userService.getUrl() + "/user"))
                .build();
    }

    @Bean
    public UserHandler userHandler(UserService userService, EventService eventService) {
        return new UserHandler(userService, eventService);
    }

    @Bean
    public RouterFunction<ServerResponse> userInfo(UserHandler userHandler) {
        return RouterFunctions.route(GET("/user/{id}"), userHandler::getUserAndEventsDTO);
    }
}
