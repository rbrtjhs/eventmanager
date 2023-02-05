package com.robertjuhas.ddd.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregateEventEventUpdated implements AggregateEventEvent {

    private ZonedDateTime time;
    private long capacity;
    private String place;
    private String title;
}
