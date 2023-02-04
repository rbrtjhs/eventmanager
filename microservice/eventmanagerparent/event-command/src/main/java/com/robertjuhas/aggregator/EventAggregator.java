package com.robertjuhas.aggregator;

import com.robertjuhas.dto.ddd.command.CreateEventCommandDTO;
import com.robertjuhas.dto.ddd.event.EventCreatedEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class EventAggregator {
    private String id;
    private EventRootEntity rootEntity;

    public EventCreatedEvent process(CreateEventCommandDTO command) {
        this.id = UUID.randomUUID().toString();
        this.rootEntity = new EventRootEntity(command);
        return new EventCreatedEvent(command);
    }
}
