package com.robertjuhas.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record MessagingEventNotifyUsersEventUpdated(ZonedDateTime time, long capacity, String place, String title,
                                                    List<String> users) implements MessagingEvent {
}
