package com.robertjuhas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Table(name = "event")
@Entity
@NoArgsConstructor
public class EventEntity {
    @Id
    @Column(name = "aggregate_id")
    private String aggregateID;

    private String title;

    private String place;

    private ZonedDateTime time;

    @Column(name = "user_id")
    private Long userID;

    @OneToMany(mappedBy = "aggregateID", cascade = {CascadeType.ALL})
    private List<EventProcessed> eventProcessed;
}
