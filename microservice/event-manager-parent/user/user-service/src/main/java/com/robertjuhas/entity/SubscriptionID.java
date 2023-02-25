package com.robertjuhas.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class SubscriptionID implements Serializable {
    @Column(name = "subscriber")
    private long subscriber;
    @Column(name = "subscribed_to")
    private long subscribedTo;
}
