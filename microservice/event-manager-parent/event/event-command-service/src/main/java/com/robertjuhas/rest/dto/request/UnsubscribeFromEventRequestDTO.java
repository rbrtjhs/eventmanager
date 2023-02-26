package com.robertjuhas.rest.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record UnsubscribeFromEventRequestDTO(@NotBlank String aggregateID,
                                             @Positive
                                             long userID) {
}
