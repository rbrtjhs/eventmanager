package com.robertjuhas.ddd.event;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.robertjuhas.ddd.event.event.AggregateEventEventCreated;
import com.robertjuhas.ddd.event.event.AggregateEventEventUpdated;
import com.robertjuhas.ddd.event.event.AggregateEventUserSubscribed;
import ddd.event.AggregateEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "event_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = "com.robertjuhas.ddd.event.event.AggregateEventEventCreated",
                value = AggregateEventEventCreated.class
        ),
        @JsonSubTypes.Type(
                name = "com.robertjuhas.ddd.event.event.AggregateEventEventUpdated",
                value = AggregateEventEventUpdated.class
        ),
        @JsonSubTypes.Type(
                name = "com.robertjuhas.ddd.event.AggregateEventEventSubscribed",
                value = AggregateEventUserSubscribed.class
        ),
})
public interface AggregateEventEvent extends AggregateEvent {
}
