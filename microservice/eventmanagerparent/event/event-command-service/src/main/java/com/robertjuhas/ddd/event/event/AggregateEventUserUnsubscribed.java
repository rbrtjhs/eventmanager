package com.robertjuhas.ddd.event.event;

import com.robertjuhas.ddd.event.AggregateEventEvent;

public record AggregateEventUserUnsubscribed(long userID) implements AggregateEventEvent {
}
