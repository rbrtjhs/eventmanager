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

    @Type(type = "json")
    private AggregateEventEvent data;

    @ManyToOne
    @JoinColumn(name = "aggregate_id")
    private AggregateEntity aggregate;

    public EventEntity(AggregateEventEvent event) {
        this.data = event;
    }
}