package com.robertjuhas.service;

import com.robertjuhas.aggregator.EventAggregator;
import com.robertjuhas.dto.ddd.command.CreateEventCommandDTO;
import com.robertjuhas.dto.web.request.CreateEventRequestDTO;
import com.robertjuhas.entity.EventEntity;
import com.robertjuhas.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    @Transactional
    public void createEvent(CreateEventRequestDTO createEventRequestDTO) {
        var userID = "test";
        var command = new CreateEventCommandDTO(createEventRequestDTO, userID);
        var eventAggregate = new EventAggregator();
        var events = eventAggregate.apply(command);
        var eventEntities = events.stream().map(x -> new EventEntity(x)).collect(Collectors.toSet());
        eventRepository.saveAll(eventEntities);
        //TODO: publish events
    }
}
