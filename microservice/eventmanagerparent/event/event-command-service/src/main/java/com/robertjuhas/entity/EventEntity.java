package com.robertjuhas.entity;

import ddd.event.AggregateEvent;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@Data
@NoArgsConstructor
@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventID;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "aggregate_id")
    private String aggregateID;

    @Version
    private Long version;

    @Type(type = "json")
    private AggregateEvent data;

    public EventEntity(String aggregateID, AggregateEvent event) {
        this.aggregateID = aggregateID;
        this.eventType = event.getClass().getSimpleName();
        this.data = event;
    }
}