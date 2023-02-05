package com.robertjuhas.repository;


import com.robertjuhas.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByAggregateIDOrderByEventIDAsc(@Param("aggregateID") String aggregateID);
}
