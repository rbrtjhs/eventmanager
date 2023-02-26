package com.robertjuhas.messaging.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record MessagingEventNotifyUsersEventCreated(ZonedDateTime time, long capacity, String place, String title,
                                                    List<String> users, long eventID, String createdBy) implements MessagingEvent {
}
