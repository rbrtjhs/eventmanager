package com.robertjuhas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "event_processed")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EventProcessed {

    @Id
    @Column(name = "event_id")
    private Long eventID;

    @Column(name = "aggregate_id")
    private String aggregateID;

    @ManyToOne
    @JoinColumn(name = "aggregate_id", insertable = false, updatable = false)
    private EventEntity eventEntity;
}
