package com.robertjuhas.service;

import com.robertjuhas.aggregator.EventAggregator;
import com.robertjuhas.dto.ddd.command.CreateEventCommandDTO;
import com.robertjuhas.dto.web.request.CreateEventRequestDTO;
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
        var command = new CreateEventCommandDTO(createEventRequestDTO, userID);
        var eventAggregate = new EventAggregator();
        var event = eventAggregate.apply(command);
        var eventEntity = new EventEntity(event);
        eventRepository.save(eventEntity);
        kafkaMessenger.send(eventEntity);
    }
}
