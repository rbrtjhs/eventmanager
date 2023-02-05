package com.robertjuhas.service;

import com.robertjuhas.aggregator.EventAggregator;
import com.robertjuhas.ddd.command.CreateEventCommand;
import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.dto.rest.request.CreateEventRequestDTO;
import com.robertjuhas.entity.EventEntity;
import com.robertjuhas.messenger.KafkaMessenger;
import com.robertjuhas.repository.EventRepository;
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
        var userID = "test";
        var command = new CreateEventCommand(createEventRequestDTO, userID);
        var aggregate = new EventAggregator();
        var event = aggregate.process(command);
        var eventEntity = new EventEntity(aggregate.getId(), event);
        eventRepository.save(eventEntity);
        var messagingEvent = new MessagingEventEventCreated(eventEntity.getEventID().toString(),
                aggregate.getId(),
                event.time(),
                event.capacity(),
                event.place(),
                event.title(),
                event.userID());
        kafkaMessenger.send(aggregate.getId(), messagingEvent);
    }
}
