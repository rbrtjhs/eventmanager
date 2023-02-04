package com.robertjuhas.listener;

import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QueryHandler {
    public static final String EVENT_EVENTS_TOPIC = "EVENT_EVENTS";

    private EventService eventService;

    @KafkaListener(topics = {EVENT_EVENTS_TOPIC}, concurrency = "3", groupId = "event-query")
    public void handleEventCreated(MessagingEventEventCreated data) {
        System.out.println("Data recieved: " + data);
        eventService.save(data);
    }
}
