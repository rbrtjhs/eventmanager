package com.robertjuhas.listener;

import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventUpdated;
import com.robertjuhas.messaging.kafka.UserTopic;
import com.robertjuhas.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventListener {
    private NotificationService notificationService;

    @KafkaListener(topics = {UserTopic.TOPIC_NOTIFY_USERS_EVENT_CREATED})
    public void handleEventCreated(MessagingEventNotifyUsersEventCreated data, Acknowledgment acknowledgment) {
        notificationService.process(data);
        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = {UserTopic.TOPIC_NOTIFY_USERS_EVENT_UPDATED})
    public void handleEventUpdated(MessagingEventNotifyUsersEventUpdated data, Acknowledgment acknowledgment) {
        notificationService.process(data);
        acknowledgment.acknowledge();
    }
}
