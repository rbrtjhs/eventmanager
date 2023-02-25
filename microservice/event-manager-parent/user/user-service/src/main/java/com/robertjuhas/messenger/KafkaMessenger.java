package com.robertjuhas.messenger;

import com.robertjuhas.messaging.dto.MessagingEvent;
import com.robertjuhas.messaging.dto.MessagingEventEventUpdated;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventUpdated;
import com.robertjuhas.messaging.kafka.UserTopic;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class KafkaMessenger {

    private KafkaTemplate<String, MessagingEvent> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void eventCreatedNotifyUsers(MessagingEventNotifyUsersEventCreated eventCreated) {
        kafkaTemplate.send(UserTopic.TOPIC_NOTIFY_USERS_EVENT_CREATED, eventCreated);
    }

    @Transactional("kafkaTransactionManager")
    public void eventUpdatedNotifyUsers(MessagingEventNotifyUsersEventUpdated eventUpdated) {
        kafkaTemplate.send(UserTopic.TOPIC_NOTIFY_USERS_EVENT_UPDATED, eventUpdated);
    }
}
