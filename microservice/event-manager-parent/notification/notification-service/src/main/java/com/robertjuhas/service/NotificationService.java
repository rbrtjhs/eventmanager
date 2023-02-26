package com.robertjuhas.service;

import com.robertjuhas.entity.NotificationEvent;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventUpdated;
import com.robertjuhas.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;

    @Transactional("transactionManager")
    public void process(MessagingEventNotifyUsersEventCreated data) {
        var event = notificationRepository.findById(data.eventID());
        if (event.isEmpty()) {
            for (String address : data.users()) {
                System.out.println("""
                        Hey %s,there is a new event '%s' organized by %s. The place of event is %s
                        """.formatted(address, data.title(), data.createdBy(), data.place()));
            }
            notificationRepository.save(new NotificationEvent(data.eventID()));
        }
    }

    @Transactional("transactionManager")
    public void process(MessagingEventNotifyUsersEventUpdated data) {
        var event = notificationRepository.findById(data.eventID());
        if (event.isEmpty()) {
            for (String address : data.users()) {
                System.out.println("""
                            Hey %s, the event %s you are subscribed to has been updated. Please check it out!
                            """.formatted(address, data.title()));
            }
            notificationRepository.save(new NotificationEvent(data.eventID()));
        }
    }
}
