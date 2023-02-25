package com.robertjuhas.unit;

import com.robertjuhas.aggregator.UserSubscribedData;
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
import com.robertjuhas.messenger.KafkaMessenger;
import com.robertjuhas.repository.AggregateRepository;
import com.robertjuhas.rest.dto.request.SubscribeToEventRequestDTO;
import com.robertjuhas.rest.dto.request.UpdateEventRequestDTO;
import com.robertjuhas.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceUnitTest {

    @Mock
    private AggregateRepository aggregateRepository;

    @Mock
    private KafkaMessenger kafkaMessenger;

    private EventService eventService;

    @BeforeEach
    public void setup() {
        eventService = new EventService(aggregateRepository, kafkaMessenger);
    }

    @Test
    public void createEvent() {
        var aggregateEntity = createAggregateEntity();
        addCreatedEvent(aggregateEntity);
        when(aggregateRepository.save(any())).thenReturn(aggregateEntity);

        var time = ZonedDateTime.now();
        var title = "title";
        var place = "place";
        var capacity = 50L;
        var createdBy = 5;
        var command = new CreateEventCommand(time, capacity, place, title, createdBy);
        var aggregateID = eventService.createEvent(command);
        assertThat(aggregateID).isNotBlank();
        verify(kafkaMessenger, times(1)).sendEventCreated(any(), any());
        verify(aggregateRepository, times(1)).save(any());
    }

    @Test
    public void updateEvent() {
        var aggregateID = "1";
        var updatedCapacity = 100L;
        var updateCommand = new UpdateEventCommand(new UpdateEventRequestDTO(aggregateID, null, updatedCapacity, null, null));

        var aggregateEntity = createAggregateEntity();
        addCreatedEvent(aggregateEntity);
        when(aggregateRepository.findByIDWithEvents(any())).thenReturn(aggregateEntity);

        var updatedAggregateEntity = createAggregateEntity();
        addCreatedEvent(updatedAggregateEntity);
        addUpdatedEvent(updatedAggregateEntity, updatedCapacity);
        when(aggregateRepository.save(any())).thenReturn(updatedAggregateEntity);

        eventService.updateEvent(updateCommand);
        verify(aggregateRepository, times(1)).findByIDWithEvents(any());
        verify(kafkaMessenger, times(1)).sendEventUpdated(any(), any());
    }

    private void addUpdatedEvent(AggregateEntity aggregateEntity, long updatedCapacity) {
        var updatedEvent = new EventEntity(new AggregateEventEventUpdated(null, updatedCapacity, null, null));
        updatedEvent.setEventID(2L);
        aggregateEntity.addEvent(updatedEvent);
    }

    @Test
    public void subscribeToEvent() {
        var subscribedUser = 5L;
        var aggregateID = "1";
        var subscribeToEventCommand = new SubscribeToEventCommand(new SubscribeToEventRequestDTO(aggregateID), subscribedUser);

        var aggregateEntity = createAggregateEntity();
        addCreatedEvent(aggregateEntity);
        when(aggregateRepository.findByIDWithEvents(any())).thenReturn(aggregateEntity);

        var updatedAggregateEntity = createAggregateEntity();
        addCreatedEvent(updatedAggregateEntity);
        addSubscribedEvent(updatedAggregateEntity, subscribedUser, subscribeToEventCommand.getTime());
        when(aggregateRepository.save(any())).thenReturn(updatedAggregateEntity);

        eventService.subscribeToEvent(subscribeToEventCommand);
        verify(aggregateRepository, times(1)).findByIDWithEvents(any());
        verify(kafkaMessenger, times(1)).sendUserSubscription(any(), any());
    }

    @Test
    public void unsubscribeFromEvent() {
        var unsubscribingUser = 5L;
        var aggregateID = "1";
        var subscribeToEventCommand = new UnsubscribeFromEventCommand(aggregateID, unsubscribingUser);

        var aggregateEntity = createAggregateEntity();
        addCreatedEvent(aggregateEntity);
        addSubscribedEvent(aggregateEntity, unsubscribingUser, ZonedDateTime.now());
        when(aggregateRepository.findByIDWithEvents(any())).thenReturn(aggregateEntity);

        var updatedAggregateEntity = createAggregateEntity();
        addCreatedEvent(updatedAggregateEntity);
        addUnsubscribedEvent(updatedAggregateEntity, unsubscribingUser);
        when(aggregateRepository.save(any())).thenReturn(updatedAggregateEntity);

        eventService.unsubscribeFromEvent(subscribeToEventCommand);
        verify(aggregateRepository, times(1)).findByIDWithEvents(any());
        verify(kafkaMessenger, times(1)).sendUserSubscription(any(), any());
    }

    private void addSubscribedEvent(AggregateEntity aggregateEntity, long userID, ZonedDateTime time) {
        var subscribedEvent = new EventEntity(new AggregateEventUserSubscribed(userID, new UserSubscribedData(time)));
        subscribedEvent.setEventID(2L);
        aggregateEntity.addEvent(subscribedEvent);
    }

    private void addUnsubscribedEvent(AggregateEntity aggregateEntity, long userID) {
        var unsubscribedEvent = new EventEntity(new AggregateEventUserUnsubscribed(userID));
        unsubscribedEvent.setEventID(2L);
        aggregateEntity.addEvent(unsubscribedEvent);

    }

    private AggregateEntity createAggregateEntity() {
        var returnValue = new AggregateEntity();
        returnValue.setEvents(new ArrayList<>());
        return returnValue;
    }

    private void addCreatedEvent(AggregateEntity aggregateEntity) {
        var time = ZonedDateTime.now();
        var title = "title";
        var place = "place";
        var capacity = 50L;
        var createdBy = 5;
        var createdEvent = new EventEntity(new AggregateEventEventCreated(time, capacity, place, title, createdBy));
        createdEvent.setEventID(1L);
        aggregateEntity.addEvent(createdEvent);
    }
}
