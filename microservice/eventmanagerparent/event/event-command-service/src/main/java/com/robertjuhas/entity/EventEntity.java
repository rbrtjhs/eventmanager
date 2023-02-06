package com.robertjuhas.entity;

import com.robertjuhas.ddd.event.AggregateEventEvent;
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

    @Column(name = "aggregate_id")
    private String aggregateID;

    //TODO: fix this, this doesn't work
    @Version
    private Long version;

    @Type(type = "json")
    private AggregateEventEvent data;

    public EventEntity(String aggregateID, AggregateEventEvent event) {
        this.aggregateID = aggregateID;
        this.data = event;
    }
}