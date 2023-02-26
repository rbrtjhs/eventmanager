package com.robertjuhas.listener;

import com.robertjuhas.messaging.dto.MessagingEventEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventEventSubscription;
import com.robertjuhas.messaging.dto.MessagingEventEventUpdated;
import com.robertjuhas.messaging.kafka.EventTopic;
import com.robertjuhas.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventListener {

    private EventService eventService;

    @KafkaListener(topics = {EventTopic.TOPIC_EVENT_CREATED}, concurrency = "1")
    public void handleEventCreated(MessagingEventEventCreated data, Acknowledgment acknowledgment) {
        eventService.save(data);
        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = {EventTopic.TOPIC_EVENT_UPDATED,}, concurrency = "1")
    public void handleEventUpdated(MessagingEventEventUpdated data, Acknowledgment acknowledgment) {
        eventService.update(data);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {EventTopic.TOPIC_EVENT_SUBSCRIPTION}, concurrency = "3")
    public void handleEventSubscription(MessagingEventEventSubscription data, Acknowledgment acknowledgment) {
        eventService.subscription(data);
        acknowledgment.acknowledge();
    }
}
