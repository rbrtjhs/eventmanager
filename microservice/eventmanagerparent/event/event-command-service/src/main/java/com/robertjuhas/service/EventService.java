package com.robertjuhas.service;

import com.robertjuhas.aggregator.EventAggregator;
import com.robertjuhas.ddd.command.event.CreateEventCommand;
import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.ddd.command.event.UpdateEventCommand;
import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.dto.messaging.MessagingEventEventUpdated;
import com.robertjuhas.dto.messaging.MessagingEventEventSubscription;
import com.robertjuhas.entity.EventEntity;
import com.robertjuhas.messenger.KafkaMessenger;
import com.robertjuhas.repository.EventRepository;
import com.robertjuhas.rest.dto.request.CreateEventRequestDTO;
import com.robertjuhas.rest.dto.request.SubscribeToEventRequestDTO;
import com.robertjuhas.rest.dto.request.UnsubscribeFromEventRequestDTO;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private static final long NUMBER_OF_USERS_SUBSCRIBED = 1L;
    private static final long NUMBER_OF_USERS_UNSUBSCRIBED = -1L;

    private EventRepository eventRepository;

    private KafkaMessenger kafkaMessenger;

    @Transactional("transactionManager")
    public String createEvent(CreateEventRequestDTO request) {
        var userID = 1L;
        var command = new CreateEventCommand(request, userID);
        var aggregate = new EventAggregator();
        var event = aggregate.process(command);
        var eventEntity = new EventEntity(aggregate.getId(), event);
        eventRepository.save(eventEntity);
        var messagingEvent = new MessagingEventEventCreated(eventEntity.getEventID(),
                aggregate.getId(),
                event.time(),
                event.capacity(),
                event.place(),
                event.title(),
                event.userID());
        kafkaMessenger.sendEventCreated(aggregate.getId(), messagingEvent);
        return aggregate.getId();
    }

    @Transactional("transactionManager")
    public void updateEvent(UpdateEventRequestDTO request) {
        var events = eventRepository.findByAggregateIDOrderByEventIDAsc(request.aggregateID());
        checkEventExists(events);
        var aggregate = new EventAggregator(events, request.aggregateID());
        var aggregateEvent = aggregate.process(new UpdateEventCommand(request));
        if (aggregateEvent != null) {
            var eventEntity = new EventEntity(aggregate.getId(), aggregateEvent);
            eventRepository.save(eventEntity);
            var messagingEvent = new MessagingEventEventUpdated(eventEntity.getEventID(), aggregate.getId(), aggregateEvent.getTime(), aggregateEvent.getCapacity(), aggregateEvent.getPlace(), aggregateEvent.getTitle());
            kafkaMessenger.sendEventUpdated(aggregate.getId(), messagingEvent);
        }
    }

    @Transactional("transactionManager")
    public void subscribeToEvent(SubscribeToEventRequestDTO request) {
        long userID = 1L;
        var events = eventRepository.findByAggregateIDOrderByEventIDAsc(request.aggregateID());
        checkEventExists(events);
        var aggregate = new EventAggregator(events, request.aggregateID());
        var aggregateEvent = aggregate.process(new SubscribeToEventCommand(userID));
        var eventEntity = new EventEntity(aggregate.getId(), aggregateEvent);
        eventRepository.save(eventEntity);
        var messagingEvent = new MessagingEventEventSubscription(eventEntity.getEventID(), aggregate.getId(), NUMBER_OF_USERS_SUBSCRIBED);
        kafkaMessenger.sendUserSubscription(aggregate.getId(), messagingEvent);
    }

    @Transactional("transactionManager")
    public void unsubscribeFromEvent(UnsubscribeFromEventRequestDTO request) {
        long userID = 1L;
        var events = eventRepository.findByAggregateIDOrderByEventIDAsc(request.aggregateID());
        checkEventExists(events);
        var aggregate = new EventAggregator(events, request.aggregateID());
        var aggregateEvent = aggregate.process(new UnsubscribeFromEventCommand(userID));
        var eventEntity = new EventEntity(aggregate.getId(), aggregateEvent);
        eventRepository.save(eventEntity);
        var messagingEvent = new MessagingEventEventSubscription(eventEntity.getEventID(), aggregate.getId(), NUMBER_OF_USERS_UNSUBSCRIBED);
        kafkaMessenger.sendUserSubscription(aggregate.getId(), messagingEvent);
    }

    private void checkEventExists(List<EventEntity> events) {
        if (events == null || events.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
