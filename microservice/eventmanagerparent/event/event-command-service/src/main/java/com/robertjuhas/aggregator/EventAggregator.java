package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.command.CreateEventCommand;
import com.robertjuhas.ddd.command.UpdateEventCommand;
import com.robertjuhas.ddd.event.AggregateEventEventCreated;
import com.robertjuhas.ddd.event.AggregateEventEventUpdated;
import com.robertjuhas.entity.EventEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class EventAggregator {

    @Getter
    private String id;
    private EventRootEntity rootEntity;

    public EventAggregator(List<EventEntity> eventEntities, String aggregateID) {
        this.id = aggregateID;
        for (EventEntity eventEntity : eventEntities) {
            try {
                this.getClass().getMethod("apply", eventEntity.getData().getClass()).invoke(this, eventEntity.getData());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AggregateEventEventCreated process(CreateEventCommand command) {
        this.id = UUID.randomUUID().toString();
        this.rootEntity = new EventRootEntity(command);
        return new AggregateEventEventCreated(command);
    }

    public AggregateEventEventUpdated process(UpdateEventCommand command) {
        var returnValue = AggregateEventEventUpdated.builder();
        if (this.rootEntity.getCapacity() != command.capacity()) {
            this.rootEntity.setCapacity(command.capacity());
            returnValue.capacity(command.capacity());
        }
        if (!this.rootEntity.getPlace().equals(command.place())) {
            this.rootEntity.setPlace(command.place());
            returnValue.place(command.place());
        }
        if (!this.rootEntity.getTitle().equals(command.title())) {
            this.rootEntity.setTitle(command.title());
            returnValue.title(command.title());
        }
        if (!this.rootEntity.getTime().equals(command.time())) {
            this.rootEntity.setTime(command.time());
            returnValue.time(command.time());
        }
        return returnValue.build();
    }

    public void apply(AggregateEventEventCreated eventCreated) {
        this.rootEntity = new EventRootEntity(eventCreated.time(), eventCreated.capacity(), eventCreated.place(), eventCreated.title(), eventCreated.userID(), new ArrayList<>());
    }
}
