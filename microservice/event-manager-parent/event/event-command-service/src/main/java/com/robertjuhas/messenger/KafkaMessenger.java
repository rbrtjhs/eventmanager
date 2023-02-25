package com.robertjuhas.messenger;

import com.robertjuhas.messaging.dto.*;
import com.robertjuhas.messaging.kafka.EventTopic;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class KafkaMessenger {

    private KafkaTemplate<String, MessagingEvent> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void sendEventCreated(String aggregateID, MessagingEventEventCreated eventCreated) {
        kafkaTemplate.send(EventTopic.TOPIC_EVENT_CREATED, aggregateID, eventCreated);
    }

    @Transactional("kafkaTransactionManager")
    public void sendEventUpdated(String aggregateID, MessagingEventEventUpdated eventUpdated) {
        kafkaTemplate.send(EventTopic.TOPIC_EVENT_UPDATED, aggregateID, eventUpdated);
    }

    @Transactional("kafkaTransactionManager")
    public void sendUserSubscription(String aggregateID, MessagingEventEventSubscription userSubscribed) {
        kafkaTemplate.send(EventTopic.TOPIC_EVENT_SUBSCRIPTION, aggregateID, userSubscribed);
    }
}
