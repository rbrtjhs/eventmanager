package com.robertjuhas.service;

import com.robertjuhas.aggregator.EventAggregator;
import com.robertjuhas.ddd.command.CreateEventCommand;
import com.robertjuhas.ddd.command.UpdateEventCommand;
import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.dto.messaging.MessagingEventEventUpdated;
import com.robertjuhas.entity.EventEntity;
import com.robertjuhas.messenger.KafkaMessenger;
import com.robertjuhas.repository.EventRepository;
import com.robertjuhas.rest.dto.request.CreateEventRequestDTO;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    private KafkaMessenger kafkaMessenger;

    @Transactional("transactionManager")
    public void createEvent(CreateEventRequestDTO createEventRequestDTO) {
        var userID = 1L;
        var command = new CreateEventCommand(createEventRequestDTO, userID);
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
        kafkaMessenger.send(aggregate.getId(), messagingEvent);
    }

    public void updateEvent(UpdateEventRequestDTO updateEventRequestDTO) {
        var events = eventRepository.findByAggregateIDOrderByEventIDAsc(updateEventRequestDTO.aggregateID());
        var aggregate = new EventAggregator(events, updateEventRequestDTO.aggregateID());
        var aggregateEvent = aggregate.process(new UpdateEventCommand(updateEventRequestDTO));
        var eventEntity = new EventEntity(aggregate.getId(), aggregateEvent);
        eventRepository.save(eventEntity);
        var messagingEvent = new MessagingEventEventUpdated(eventEntity.getEventID(), aggregate.getId(), aggregateEvent.getTime(), aggregateEvent.getCapacity(), aggregateEvent.getPlace(), aggregateEvent.getTitle());
        kafkaMessenger.send(aggregate.getId(), messagingEvent);
    }
}
