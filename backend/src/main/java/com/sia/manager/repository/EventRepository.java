package com.sia.manager.repository;

import java.util.List;
import com.sia.manager.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByEventNameContaining(String event);
    List<Event> findByPublished(boolean published);
}
