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
import com.robertjuhas.exception.EventManagerException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@NoArgsConstructor
public class EventAggregate {

    private static final long PROHIBIT_CHANGING_TIME_BEFORE_EVENT_IN_HOURS = 6;

    @Getter
    private String id;
    private EventRootEntity rootEntity;

    public EventAggregate(AggregateEntity aggregate) {
        this.id = aggregate.getId();
        for (EventEntity eventEntity : aggregate.getEvents()) {
            try {
                this.getClass().getMethod("apply", eventEntity.getData().getClass()).invoke(this, eventEntity.getData());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Long> getUsers() {
        return new ArrayList<>(rootEntity.getSubscribers().keySet());
    }

    public AggregateEventEventCreated process(@NotNull CreateEventCommand command) {
        this.id = UUID.randomUUID().toString();
        rootEntity = new EventRootEntity(command);
        return new AggregateEventEventCreated(command);
    }

    public AggregateEventEventUpdated process(@NotNull UpdateEventCommand command) {
        var returnValue = AggregateEventEventUpdated.builder();
        var updated = false;
        if (command.capacity() > 0 && rootEntity.getCapacity() != command.capacity()) {
            rootEntity.setCapacity(command.capacity());
            returnValue.capacity(command.capacity());
            updated = true;
        }
        if (StringUtils.isNotBlank(command.place()) && !rootEntity.getPlace().equals(command.place())) {
            rootEntity.setPlace(command.place());
            returnValue.place(command.place());
            updated = true;
        }
        if (StringUtils.isNotBlank(command.title()) && !rootEntity.getTitle().equals(command.title())) {
            rootEntity.setTitle(command.title());
            returnValue.title(command.title());
            updated = true;
        }
        if (Objects.nonNull(command.time()) && !rootEntity.getTime().equals(command.time())) {
            validateTime(command.time());
            rootEntity.setTime(command.time());
            returnValue.time(command.time());
            updated = true;
        }
        return updated ? returnValue.build() : null;
    }

    public AggregateEventUserSubscribed process(@NotNull SubscribeToEventCommand command) {
        if (rootEntity.getSubscribers().containsKey(command.getUserID())) {
            throw new EventManagerException("User is already subscribed.");
        }
        if (rootEntity.getSubscribers().size() == rootEntity.getCapacity()) {
            throw new EventManagerException("Event is full.");
        }
        var userData = new UserSubscribedData(command.getTime());
        rootEntity.getSubscribers().put(command.getUserID(), userData);
        return new AggregateEventUserSubscribed(command.getUserID(), userData);
    }

    public AggregateEventUserUnsubscribed process(@NotNull UnsubscribeFromEventCommand command) {
        if (!rootEntity.getSubscribers().containsKey(command.userID())) {
            throw new EventManagerException("User is not subscribed.");
        }
        rootEntity.getSubscribers().remove(command.userID());
        return new AggregateEventUserUnsubscribed(command.userID());
    }

    private void validateTime(ZonedDateTime zonedDateTime) {
        if (rootEntity.getTime().isBefore(zonedDateTime)) {
            throw new EventManagerException("Cannot change time before now.");
        } else if (ChronoUnit.HOURS.between(zonedDateTime, rootEntity.getTime()) <= PROHIBIT_CHANGING_TIME_BEFORE_EVENT_IN_HOURS) {
            throw new EventManagerException("Cannot change time " + PROHIBIT_CHANGING_TIME_BEFORE_EVENT_IN_HOURS + " hours before event starts.");
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
