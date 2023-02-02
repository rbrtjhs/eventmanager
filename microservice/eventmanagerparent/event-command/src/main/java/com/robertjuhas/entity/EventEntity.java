package com.robertjuhas.entity;

import com.robertjuhas.ddd.Event;
import com.robertjuhas.ddd.EventData;
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
    private EventData data;

    public EventEntity(Event event) {
        this.eventType = event.getName();
        this.aggregateID = event.getAggregateID();
        this.data = event.getData();
    }
}