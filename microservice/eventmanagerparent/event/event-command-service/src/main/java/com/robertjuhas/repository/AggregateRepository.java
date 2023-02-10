package com.robertjuhas.repository;

import com.robertjuhas.entity.AggregateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AggregateRepository extends JpaRepository<AggregateEntity, String> {

    @Query(value = "SELECT a FROM AggregateEntity a JOIN a.events e WHERE a.id = :id ORDER BY e.eventID")
    AggregateEntity findByIDWithEvents(@Param("id") String id);
}
