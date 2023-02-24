package com.robertjuhas.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "aggregate")
@NoArgsConstructor
public class AggregateEntity {
    @Id
    private String id;

    private String type;

    @Version
    private long version;

    @OneToMany(mappedBy = "aggregate", cascade = CascadeType.ALL)
    @OptimisticLock(excluded = false)
    private List<EventEntity> events;

    public AggregateEntity(String id, String type) {
        this.id = id;
        this.type = type;
        this.events = new ArrayList<>();
    }

    public void addEvent(EventEntity event) {
        this.events.add(event);
        event.setAggregate(this);
    }
}
