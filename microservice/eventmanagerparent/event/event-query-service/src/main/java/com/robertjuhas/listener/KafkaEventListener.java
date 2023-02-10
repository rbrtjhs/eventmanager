package com.robertjuhas.listener;

import com.robertjuhas.dto.messaging.MessagingEventEventCreated;
import com.robertjuhas.dto.messaging.MessagingEventEventSubscription;
import com.robertjuhas.dto.messaging.MessagingEventEventUpdated;
import com.robertjuhas.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventListener {
    public static final String TOPIC_EVENT_CREATED = "EVENT_CREATED";
    public static final String TOPIC_EVENT_UPDATED = "EVENT_UPDATED";
    public static final String TOPIC_EVENT_SUBSCRIPTION = "EVENT_SUBSCRIPTION";

    private static final String GROUP_ID = "event-query";

    private EventService eventService;

    @KafkaListener(topics = {TOPIC_EVENT_CREATED}, concurrency = "1", groupId = GROUP_ID)
    public void handleEventCreated(MessagingEventEventCreated data, Acknowledgment acknowledgment) {
        eventService.save(data);
        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = {TOPIC_EVENT_UPDATED}, concurrency = "1", groupId = GROUP_ID)
    public void handleEventUpdated(MessagingEventEventUpdated data, Acknowledgment acknowledgment) {
        eventService.update(data);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {TOPIC_EVENT_SUBSCRIPTION}, concurrency = "3", groupId = GROUP_ID)
    public void handleEventSubscription(MessagingEventEventSubscription data, Acknowledgment acknowledgment) {
        eventService.subscription(data);
        acknowledgment.acknowledge();
    }
}
