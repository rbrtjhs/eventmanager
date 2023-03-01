package com.robertjuhas.service;

import com.robertjuhas.messaging.dto.MessagingEventEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventEventSubscription;
import com.robertjuhas.messaging.dto.MessagingEventEventUpdated;
import com.robertjuhas.model.EventEntity;
import com.robertjuhas.model.EventProcessed;
import com.robertjuhas.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    public List<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    public List<EventEntity> findByUser(long userID) {
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
            eventEntity.setCapacity(eventCreated.capacity());
            eventRepository.save(eventEntity);
        }
    }

    @Transactional
    public void update(MessagingEventEventUpdated eventUpdated) {
        var eventExists = eventRepository.findById(eventUpdated.aggregateID());
        checkEvent(eventExists);
        var eventEntity = eventExists.get();
        boolean eventIsNotProcessed = checkIfEventIsAlreadyProcessed(eventEntity, eventUpdated.eventID());
        if (eventIsNotProcessed) {
            if (eventUpdated.capacity() != null) {
                eventEntity.setCapacity(eventUpdated.capacity());
            }
            if (eventUpdated.time() != null) {
                eventEntity.setTime(eventUpdated.time());
            }
            if (eventUpdated.place() != null) {
                eventEntity.setPlace(eventUpdated.place());
            }
            if (eventUpdated.title() != null) {
                eventEntity.setTitle(eventUpdated.title());
            }
            var eventProcessed = new EventProcessed();
            eventProcessed.setEventID(eventUpdated.eventID());
            eventProcessed.setAggregateID(eventUpdated.aggregateID());
            eventEntity.getEventProcessed().add(eventProcessed);
            eventRepository.save(eventEntity);
        }
    }

    @Transactional
    public void subscription(MessagingEventEventSubscription data) {
        var eventExists = eventRepository.findById(data.aggregateID());
        checkEvent(eventExists);
        var eventEntity = eventExists.get();
        boolean eventIsNotProcessed = checkIfEventIsAlreadyProcessed(eventEntity, data.eventID());
        if (eventIsNotProcessed) {
            eventEntity.setActualCapacity(eventEntity.getCapacity() + data.value());
            var eventProcessed = new EventProcessed();
            eventProcessed.setEventID(data.eventID());
            eventProcessed.setAggregateID(data.aggregateID());
            eventEntity.getEventProcessed().add(eventProcessed);
            eventRepository.save(eventEntity);
        }
    }

    private void checkEvent(Optional<EventEntity> eventExists) {
        if (eventExists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private boolean checkIfEventIsAlreadyProcessed(EventEntity entity, long eventID) {
        return entity.getEventProcessed().stream().map(x -> x.getEventID()).noneMatch(x -> x.equals(eventID));
    }
}
