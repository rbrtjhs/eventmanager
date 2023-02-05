package com.robertjuhas.ddd.event;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ddd.event.AggregateEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "event_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = "com.robertjuhas.ddd.event.AggregateEventEventCreated",
                value = AggregateEventEventCreated.class
        ),
        @JsonSubTypes.Type(
                name = "com.robertjuhas.ddd.event.AggregateEventEventUpdated",
                value = AggregateEventEventUpdated.class
        ),
})
public interface AggregateEventEvent extends AggregateEvent {
}
