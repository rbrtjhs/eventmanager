package com.robertjuhas.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {

    @EmbeddedId
    private SubscriptionID id;

    @ManyToOne
    @JoinColumn(name = "subscriber", referencedColumnName = "id", insertable = false, updatable = false)
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "subscribed_to", referencedColumnName = "id", insertable = false, updatable = false)
    private User subscribedTo;
}
