package com.robertjuhas.ddd.event.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robertjuhas.ddd.event.AggregateEventEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AggregateEventEventUpdated implements AggregateEventEvent {

    private ZonedDateTime time;
    private Long capacity;
    private String place;
    private String title;
}
