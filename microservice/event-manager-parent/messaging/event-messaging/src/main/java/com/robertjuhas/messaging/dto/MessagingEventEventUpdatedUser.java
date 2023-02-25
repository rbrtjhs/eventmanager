package com.robertjuhas.messaging.dto;

import java.util.List;

public record MessagingEventEventUpdatedUser(long eventID, String aggregateID, List<Long> userIDs) implements MessagingEvent{
}
