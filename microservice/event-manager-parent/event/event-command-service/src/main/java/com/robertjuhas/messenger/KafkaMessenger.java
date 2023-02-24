package com.robertjuhas.messenger;

import com.robertjuhas.dto.messaging.*;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class KafkaMessenger {
    public static final String TOPIC_EVENT_CREATED = "EVENT_CREATED";
    public static final String TOPIC_EVENT_UPDATED = "EVENT_UPDATED";
    public static final String TOPIC_EVENT_SUBSCRIPTION = "EVENT_SUBSCRIPTION";

    private KafkaTemplate<String, MessagingEvent> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void sendEventCreated(String aggregateID, MessagingEventEventCreated eventCreated) {
        kafkaTemplate.send(TOPIC_EVENT_CREATED, aggregateID, eventCreated);
    }

    @Transactional("kafkaTransactionManager")
    public void sendEventUpdated(String aggregateID, MessagingEventEventUpdated eventUpdated) {
        kafkaTemplate.send(TOPIC_EVENT_UPDATED, aggregateID, eventUpdated);
    }

    @Transactional("kafkaTransactionManager")
    public void sendUserSubscription(String aggregateID, MessagingEventEventSubscription userSubscribed) {
        kafkaTemplate.send(TOPIC_EVENT_SUBSCRIPTION, aggregateID, userSubscribed);
    }
}
