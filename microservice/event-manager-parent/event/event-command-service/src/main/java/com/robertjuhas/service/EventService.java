package com.robertjuhas.service;

import com.robertjuhas.aggregator.AggregateType;
import com.robertjuhas.aggregator.EventAggregate;
import com.robertjuhas.ddd.command.event.CreateEventCommand;
import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.ddd.command.event.UpdateEventCommand;
import com.robertjuhas.ddd.event.AggregateEventEvent;
import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.dto.messaging.MessagingEventEventSubscription;
import com.robertjuhas.dto.messaging.MessagingEventEventUpdated;
import com.robertjuhas.entity.AggregateEntity;
import com.robertjuhas.entity.EventEntity;
import com.robertjuhas.messenger.KafkaMessenger;
import com.robertjuhas.repository.AggregateRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EventService {
    private static final long NUMBER_OF_USERS_SUBSCRIBED = 1L;
    private static final long NUMBER_OF_USERS_UNSUBSCRIBED = -1L;

    private AggregateRepository aggregateRepository;

    private KafkaMessenger kafkaMessenger;

    @Transactional("transactionManager")
    public String createEvent(CreateEventCommand command) {
        var aggregate = new EventAggregate();
        var aggregateEvent = aggregate.process(command);
        var aggregateEntity = new AggregateEntity(aggregate.getId(), AggregateType.EVENT.name());
        var eventEntity = saveEvent(aggregateEntity, aggregateEvent);
        var messagingEvent = new MessagingEventEventCreated(eventEntity.getEventID(),
                aggregate.getId(),
                aggregateEvent.time(),
                aggregateEvent.capacity(),
                aggregateEvent.place(),
                aggregateEvent.title(),
                aggregateEvent.userID());
        kafkaMessenger.sendEventCreated(aggregate.getId(), messagingEvent);
        return aggregate.getId();
    }

    @Transactional("transactionManager")
    public void updateEvent(UpdateEventCommand updateEventCommand) {
        var aggregateEntity = aggregateRepository.findByIDWithEvents(updateEventCommand.aggregateID());
        checkAggregateExists(aggregateEntity);
        var aggregate = new EventAggregate(aggregateEntity);
        var aggregateEvent = aggregate.process(updateEventCommand);
        if (aggregateEvent != null) {
            var eventEntity = saveEvent(aggregateEntity, aggregateEvent);
            var messagingEvent = new MessagingEventEventUpdated(eventEntity.getEventID(), aggregate.getId(), aggregateEvent.getTime(), aggregateEvent.getCapacity(), aggregateEvent.getPlace(), aggregateEvent.getTitle());
            kafkaMessenger.sendEventUpdated(aggregate.getId(), messagingEvent);
        }
    }

    @Transactional("transactionManager")
    public void subscribeToEvent(SubscribeToEventCommand command) {
        var aggregateEntity = aggregateRepository.findByIDWithEvents(command.getAggregateID());
        checkAggregateExists(aggregateEntity);
        var aggregate = new EventAggregate(aggregateEntity);
        var aggregateEvent = aggregate.process(command);
        var eventEntity = saveEvent(aggregateEntity, aggregateEvent);
        var messagingEvent = new MessagingEventEventSubscription(eventEntity.getEventID(), aggregate.getId(), NUMBER_OF_USERS_SUBSCRIBED);
        kafkaMessenger.sendUserSubscription(aggregate.getId(), messagingEvent);
    }

    @Transactional("transactionManager")
    public void unsubscribeFromEvent(UnsubscribeFromEventCommand command) {
        var aggregateEntity = aggregateRepository.findByIDWithEvents(command.aggregateID());
        checkAggregateExists(aggregateEntity);
        var aggregate = new EventAggregate(aggregateEntity);
        var aggregateEvent = aggregate.process(command);
        var eventEntity = saveEvent(aggregateEntity, aggregateEvent);
        var messagingEvent = new MessagingEventEventSubscription(eventEntity.getEventID(), aggregate.getId(), NUMBER_OF_USERS_UNSUBSCRIBED);
        kafkaMessenger.sendUserSubscription(aggregate.getId(), messagingEvent);
    }

    private void checkAggregateExists(AggregateEntity aggregate) {
        if (aggregate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private EventEntity saveEvent(AggregateEntity aggregateEntity, AggregateEventEvent aggregateEvent) {
        var eventEntity = new EventEntity(aggregateEvent);
        aggregateEntity.addEvent(eventEntity);
        aggregateEntity = aggregateRepository.save(aggregateEntity);
        return aggregateEntity.getEvents().get(aggregateEntity.getEvents().size() - 1);
    }
}
