package com.robertjuhas.repository;

import com.robertjuhas.entity.Subscription;
import com.robertjuhas.entity.SubscriptionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionID> {
    @Query("SELECT s FROM Subscription s WHERE s.subscribedTo.id = ?1")
    List<Subscription> findBySubscribedTo(long subscribedTo);
}
