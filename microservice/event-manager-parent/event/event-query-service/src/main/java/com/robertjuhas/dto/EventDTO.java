package com.robertjuhas.dto;

import com.robertjuhas.model.EventEntity;

import java.time.ZonedDateTime;

public record EventDTO(String id, String title, String place, ZonedDateTime time) {
    public EventDTO(EventEntity entity) {
        this(entity.getAggregateID(), entity.getTitle(), entity.getPlace(), entity.getTime());
    }
}
