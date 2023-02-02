package com.robertjuhas.service;

import com.robertjuhas.model.EventEntity;
import com.robertjuhas.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    public List<EventEntity> getAll() {
        return eventRepository.findAll();
    }

    public List<EventEntity> findByUserID(String userID) {
        return eventRepository.findAllByUserID(userID);
    }

    public void save(Object o) {
        System.out.println("Object: " + o);
    }
}
