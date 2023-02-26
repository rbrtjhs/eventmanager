package com.robertjuhas.rest.dto.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public record SubscribeToEventRequestDTO(
        @NotEmpty
        String aggregateID,
        @Positive
        long userID) {

}
