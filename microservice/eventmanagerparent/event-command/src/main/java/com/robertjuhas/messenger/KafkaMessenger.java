package com.robertjuhas.messenger;

import com.robertjuhas.entity.EventEntity;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class KafkaMessenger {
    public static final String TOPIC_EVENT_EVENTS = "EVENT_EVENTS";
    private KafkaTemplate<String, EventEntity> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void send(EventEntity entity) {
        kafkaTemplate.send(TOPIC_EVENT_EVENTS, entity.getAggregateID(), entity);
    }
}
