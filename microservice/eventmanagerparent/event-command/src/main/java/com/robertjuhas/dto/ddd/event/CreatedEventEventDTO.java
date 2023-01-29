package com.robertjuhas.dto.ddd.event;

import com.robertjuhas.ddd.Event;
import com.robertjuhas.ddd.EventData;
import com.robertjuhas.dto.ddd.command.CreateEventCommandDTO;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class CreatedEventEventDTO implements Event {
    private final String name = CreatedEventEventDTO.class.getSimpleName();
    private String aggregateID;
    private EventData data;

    public CreatedEventEventDTO(String aggregateID, CreateEventCommandDTO eventCommand) {
        this.aggregateID = aggregateID;
        this.data = new CreatedEventEventData(eventCommand);
    }

    public record CreatedEventEventData(
            ZonedDateTime time,
            long capacity,
            String place,
            String title,
            String userID
    ) implements EventData {
        public CreatedEventEventData(CreateEventCommandDTO command) {
            this(command.time(), command.capacity(), command.place(), command.title(), command.userID());
        }
    }
}
