package com.robertjuhas.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record MessagingEventNotifyUsersEventUpdated(String title, List<String> users, long eventID) implements MessagingEvent {
}
