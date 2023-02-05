package com.robertjuhas.aggregator;

import com.robertjuhas.ddd.command.CreateEventCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class EventRootEntity {
    private ZonedDateTime time;
    private long capacity;
    private String place;
    private String title;
    private long createdBy;
    private List<Long> subscribers;
    public EventRootEntity(CreateEventCommand command) {
        this(command.time(), command.capacity(), command.place(), command.title(), command.createdBy(), new ArrayList<>());
    }
}
