package com.robertjuhas.unit;

import com.robertjuhas.messaging.dto.MessagingEventEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventEventSubscription;
import com.robertjuhas.messaging.dto.MessagingEventEventUpdated;
import com.robertjuhas.model.EventEntity;
import com.robertjuhas.model.EventProcessed;
import com.robertjuhas.repository.EventRepository;
import com.robertjuhas.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceQueryUnitTest {


    @Mock
    private EventRepository eventRepository;

    private EventService eventService;

    @BeforeEach
    public void setup() {
        eventService = new EventService(eventRepository);
    }

    @Test
    public void testFindEventsByUserID() {
        var eventList = new ArrayList<EventEntity>();
        var eventEntity = new EventEntity();
        eventEntity.setCapacity(50);
        eventList.add(eventEntity);
        when(eventRepository.findAllByUserID(anyLong())).thenReturn(eventList);
        var list = eventService.findByUser(5);
        assertThat(list).isNotNull().isNotEmpty();
        verify(eventRepository, times(1)).findAllByUserID(anyLong());
    }

    @Test
    public void testSave() {
        when(eventRepository.findById(any())).thenReturn(Optional.empty());
        var messagingEvent = new MessagingEventEventCreated(1, "id", ZonedDateTime.now(), 50, "place", "title", 5);
        eventService.save(messagingEvent);
        verify(eventRepository, times(1)).save(any());
        verify(eventRepository, times(1)).findById("id");
    }

    @Test
    public void testSaveDuplicate() {
        when(eventRepository.findById(any())).thenReturn(Optional.ofNullable(new EventEntity()));
        var messagingEvent = new MessagingEventEventCreated(1, "id", ZonedDateTime.now(), 50, "place", "title", 5);
        eventService.save(messagingEvent);
        verify(eventRepository, times(0)).save(any());
    }

    @Test
    public void testUpdate() {
        var eventEntity = new EventEntity();
        eventEntity.setEventProcessed(new ArrayList<>());
        when(eventRepository.findById(any())).thenReturn(Optional.of(eventEntity));
        var messagingEvent = new MessagingEventEventUpdated(1, "id", ZonedDateTime.now(), null, "place", "title", new ArrayList<>());
        eventService.update(messagingEvent);
        verify(eventRepository, times(1)).findById(any());
        verify(eventRepository, times(1)).save(any());
    }

    @Test
    public void testDuplicateUpdate() {
        var eventEntity = new EventEntity();
        var eventProcessed = new EventProcessed();
        eventProcessed.setEventID(1L);
        eventEntity.setEventProcessed(List.of(eventProcessed));
        when(eventRepository.findById(any())).thenReturn(Optional.of(eventEntity));
        var messagingEvent = new MessagingEventEventUpdated(1, "id", ZonedDateTime.now(), 50L, "place", "title", List.of(5L));
        eventService.update(messagingEvent);
        verify(eventRepository, times(1)).findById(any());
        verify(eventRepository, times(0)).save(any());
    }

    @Test
    public void testSubscribe() {
        var eventEntity = new EventEntity();
        eventEntity.setEventProcessed(new ArrayList<>());
        when(eventRepository.findById(any())).thenReturn(Optional.of(eventEntity));
        var messagingEvent = new MessagingEventEventSubscription(1, "id", 1);
        eventService.subscription(messagingEvent);
        verify(eventRepository, times(1)).findById(any());
        verify(eventRepository, times(1)).save(any());
    }

    @Test
    public void testSubscribeDuplicate() {
        long eventID = 1L;
        var eventEntity = new EventEntity();
        eventEntity.setEventProcessed(new ArrayList<>());
        var eventProcessed = new EventProcessed();
        eventProcessed.setEventID(eventID);
        eventEntity.getEventProcessed().add(eventProcessed);

        when(eventRepository.findById(any())).thenReturn(Optional.of(eventEntity));
        var messagingEvent = new MessagingEventEventSubscription(eventID, "id", 1);
        eventService.subscription(messagingEvent);
        verify(eventRepository, times(1)).findById(any());
        verify(eventRepository, times(0)).save(any());
    }
}
