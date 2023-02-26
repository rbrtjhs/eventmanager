package com.robertjuhas.repository;

import com.robertjuhas.entity.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEvent, Long> {

}
