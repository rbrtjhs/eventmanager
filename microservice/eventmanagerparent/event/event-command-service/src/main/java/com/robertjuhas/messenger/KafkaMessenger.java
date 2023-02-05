package com.robertjuhas.messenger;

import com.robertjuhas.dto.messaging.MessagingEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class KafkaMessenger {
    public static final String TOPIC_EVENT_EVENTS = "EVENT_EVENTS";
    private KafkaTemplate<String, MessagingEvent> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void send(String aggregateID, MessagingEvent eventCreated) {
        kafkaTemplate.send(TOPIC_EVENT_EVENTS, aggregateID, eventCreated);
    }
}
