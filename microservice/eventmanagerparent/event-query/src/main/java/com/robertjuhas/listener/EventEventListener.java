package com.robertjuhas.listener;

import com.robertjuhas.service.EventService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventEventListener {
    public static final String EVENT_EVENTS_TOPIC = "EVENT_EVENTS";

    private EventService eventService;

    @KafkaListener(id = "event_events", topics = EVENT_EVENTS_TOPIC, concurrency = "3")
    public void listen(ConsumerRecord<String, Object> record) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " partition: " + record.partition());
        eventService.save(record);
    }
}
