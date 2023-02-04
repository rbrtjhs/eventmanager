package com.robertjuhas.service;

import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.model.EventEntity;
import com.robertjuhas.model.EventProcessed;
import com.robertjuhas.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    public List<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    public List<EventEntity> findByUserID(String userID) {
        return eventRepository.findAllByUserID(userID);
    }

    @Transactional
    public void save(MessagingEventEventCreated eventCreated) {
        var eventExists = eventRepository.findById(eventCreated.aggregateID());
        if (!eventExists.isPresent()) {
            var eventsProcessed = new ArrayList<EventProcessed>();
            var eventProcessed = new EventProcessed();
            eventProcessed.setEventID(eventCreated.eventID());
            eventProcessed.setAggregateID(eventCreated.aggregateID());
            eventsProcessed.add(eventProcessed);

            var eventEntity = new EventEntity();
            eventEntity.setEventProcessed(eventsProcessed);
            eventEntity.setTime(eventCreated.time());
            eventEntity.setTitle(eventCreated.title());
            eventEntity.setUserID(eventCreated.userID());
            eventEntity.setPlace(eventCreated.place());
            eventEntity.setAggregateID(eventCreated.aggregateID());

            eventRepository.save(eventEntity);
        }
    }
}
