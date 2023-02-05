package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.command.CreateEventCommand;
import com.robertjuhas.ddd.event.AggregateEventEventCreated;
import lombok.Getter;

import java.util.UUID;

@Getter
public class EventAggregator {
    private String id;
    private EventRootEntity rootEntity;

    public AggregateEventEventCreated process(CreateEventCommand command) {
        this.id = UUID.randomUUID().toString();
        this.rootEntity = new EventRootEntity(command);
        return new AggregateEventEventCreated(command);
    }
}
