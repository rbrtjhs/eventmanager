package com.robertjuhas.ddd.event.event;

import com.robertjuhas.aggregator.UserSubscribedData;
import com.robertjuhas.ddd.event.AggregateEventEvent;

public record AggregateEventUserSubscribed(long userID, UserSubscribedData subscribed) implements AggregateEventEvent {
}
