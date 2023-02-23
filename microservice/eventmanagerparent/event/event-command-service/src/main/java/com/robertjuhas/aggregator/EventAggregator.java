package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.command.event.CreateEventCommand;
import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.ddd.command.event.UpdateEventCommand;
import com.robertjuhas.ddd.event.event.AggregateEventEventCreated;
import com.robertjuhas.ddd.event.event.AggregateEventEventUpdated;
import com.robertjuhas.ddd.event.event.AggregateEventUserSubscribed;
import com.robertjuhas.ddd.event.event.AggregateEventUserUnsubscribed;
import com.robertjuhas.entity.AggregateEntity;
import com.robertjuhas.entity.EventEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor
public class EventAggregator {

    private static final long PROHIBIT_CHANGING_TIME_BEFORE_EVENT_IN_HOURS = 6;

    @Getter
    private String id;
    private EventRootEntity rootEntity;

    public EventAggregator(AggregateEntity aggregate) {
        this.id = aggregate.getId();
        for (EventEntity eventEntity : aggregate.getEvents()) {
            try {
                this.getClass().getMethod("apply", eventEntity.getData().getClass()).invoke(this, eventEntity.getData());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AggregateEventEventCreated process(CreateEventCommand command) {
        this.id = UUID.randomUUID().toString();
        rootEntity = new EventRootEntity(command);
        return new AggregateEventEventCreated(command);
    }

    public AggregateEventEventUpdated process(UpdateEventCommand command) {
        var returnValue = AggregateEventEventUpdated.builder();
        var updated = false;
        if (rootEntity.getCapacity() != command.capacity()) {
            rootEntity.setCapacity(command.capacity());
            returnValue.capacity(command.capacity());
            updated = true;
        }
        if (!rootEntity.getPlace().equals(command.place())) {
            rootEntity.setPlace(command.place());
            returnValue.place(command.place());
            updated = true;
        }
        if (!rootEntity.getTitle().equals(command.title())) {
            rootEntity.setTitle(command.title());
            returnValue.title(command.title());
            updated = true;
        }
        if (!rootEntity.getTime().equals(command.time())) {
            validateTime(command.time());
            rootEntity.setTime(command.time());
            returnValue.time(command.time());
            updated = true;
        }
        return updated ? returnValue.build() : null;
    }

    public AggregateEventUserSubscribed process(SubscribeToEventCommand command) {
        if (rootEntity.getSubscribers().containsKey(command.userID())) {
            throw new IllegalArgumentException("User is already subscribed.");
        }
        if (rootEntity.getSubscribers().size() == rootEntity.getCapacity()) {
            throw new IllegalArgumentException("Event is full.");
        }
        var userData = new UserSubscribedData(command.time());
        rootEntity.getSubscribers().put(command.userID(), userData);
        return new AggregateEventUserSubscribed(command.userID(), userData);
    }

    public AggregateEventUserUnsubscribed process(UnsubscribeFromEventCommand command) {
        if (!rootEntity.getSubscribers().containsKey(command.userID())) {
            throw new IllegalArgumentException("User is not subscribed.");
        }
        rootEntity.getSubscribers().remove(command.userID());
        return new AggregateEventUserUnsubscribed(command.userID());
    }

    private void validateTime(ZonedDateTime zonedDateTime) {
        if (ChronoUnit.HOURS.between(rootEntity.getTime(), zonedDateTime) <= PROHIBIT_CHANGING_TIME_BEFORE_EVENT_IN_HOURS) {
            throw new IllegalArgumentException("Cannot change time " + PROHIBIT_CHANGING_TIME_BEFORE_EVENT_IN_HOURS + " hours before event starts.");
        }
    }

    public void apply(AggregateEventEventCreated eventCreated) {
        rootEntity = new EventRootEntity(eventCreated.time(), eventCreated.capacity(), eventCreated.place(), eventCreated.title(), eventCreated.userID(), new HashMap<>());
    }

    public void apply(AggregateEventEventUpdated eventUpdated) {
        if (eventUpdated.getCapacity() != null) {
            rootEntity.setCapacity(eventUpdated.getCapacity());
        }
        if (eventUpdated.getPlace() != null) {
            rootEntity.setPlace(eventUpdated.getPlace());
        }
        if (eventUpdated.getTime() != null) {
            rootEntity.setTime(eventUpdated.getTime());
        }
        if (eventUpdated.getTitle() != null) {
            rootEntity.setTitle(eventUpdated.getTitle());
        }
    }

    public void apply(AggregateEventUserSubscribed userSubscribed) {
        rootEntity.getSubscribers().put(userSubscribed.userID(), userSubscribed.subscribed());
    }

    public void apply(AggregateEventUserUnsubscribed userUnsubscribed) {
        rootEntity.getSubscribers().remove(userUnsubscribed.userID());
    }
}
