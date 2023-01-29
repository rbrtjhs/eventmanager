package com.robertjuhas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@Table(name = "event")
@Entity
@NoArgsConstructor
public class EventEntity {
    @Id
    private String id;

    private String title;

    private String place;

    private ZonedDateTime time;

    @Column(name = "user_id")
    private String userID;
}
