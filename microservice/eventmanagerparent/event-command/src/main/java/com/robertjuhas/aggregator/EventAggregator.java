package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.Event;
import com.robertjuhas.dto.ddd.command.CreateEventCommandDTO;
import com.robertjuhas.dto.ddd.event.CreatedEventEventDTO;

import java.util.UUID;

public class EventAggregator {

    public Event apply(CreateEventCommandDTO command) {
        var uuid = UUID.randomUUID().toString();
        var createdEventEvent = new CreatedEventEventDTO(uuid, command);
        return createdEventEvent;
    }
}
