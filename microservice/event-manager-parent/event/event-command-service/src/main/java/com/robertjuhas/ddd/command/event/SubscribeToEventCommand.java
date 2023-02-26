package com.robertjuhas.ddd.command.event;

import com.robertjuhas.ddd.command.EventCommand;
import com.robertjuhas.rest.dto.request.SubscribeToEventRequestDTO;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

@Getter
public class SubscribeToEventCommand implements EventCommand {
    private String aggregateID;
    private long userID;
    private ZonedDateTime time;

    public SubscribeToEventCommand(@NotNull SubscribeToEventRequestDTO request) {
        this(request.aggregateID(), request.userID(), ZonedDateTime.now());
    }

    private SubscribeToEventCommand(
            @NotBlank String aggregateID,
            @Positive long userID,
            @NotNull ZonedDateTime time) {
        this.aggregateID = aggregateID;
        this.userID = userID;
        this.time = time;
    }
}
