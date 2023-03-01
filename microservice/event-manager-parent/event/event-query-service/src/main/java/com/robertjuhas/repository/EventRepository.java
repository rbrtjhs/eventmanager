package com.robertjuhas.repository;

import com.robertjuhas.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, String> {

    List<EventEntity> findAllByUserID(@Param("userID") long userID);
}
