package com.robertjuhas.ddd;

public interface Event {
    String getName();
    String getAggregateID();
    EventData getData();
}
