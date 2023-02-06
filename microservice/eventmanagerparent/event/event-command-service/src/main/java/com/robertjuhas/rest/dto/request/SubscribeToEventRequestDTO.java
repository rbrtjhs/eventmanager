package com.robertjuhas.rest.dto.request;


import javax.validation.constraints.NotEmpty;

public record SubscribeToEventRequestDTO(
        @NotEmpty
        String aggregateID) {

}
