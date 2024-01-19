package com.sia.manager.controllers;

import com.sia.manager.models.Event;
import com.sia.manager.repository.EventRepository;
import org.apache.naming.HandlerRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(required = false) String eventName){
        try{
            List<Event> events = new ArrayList<Event>();

            if(eventName == null)
                eventRepository.findAll().forEach(events::add);
            else
                eventRepository.findByEventNameContaining(eventName).forEach(events::add);

            if(events.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }
            return  new ResponseEntity<>(events, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id){
        Optional<Event> eventData = eventRepository.findById(id);

        if(eventData.isPresent())
            return new ResponseEntity<>(eventData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        try{
            Event _event = eventRepository.save(new Event(event.getEventName(), event.getDescription(), false));
            return new ResponseEntity<>(_event, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String id, @RequestBody Event event){
        Optional<Event> eventData = eventRepository.findById(id);

        if(eventData.isPresent()){
            Event _event = eventData.get();
            _event.setEventName(event.getEventName());
            _event.setDescription(event.getDescription());
            _event.setPublished(event.isPublished());
            return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("id") String id){
        try{
            eventRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/events")
    public ResponseEntity<HttpStatus> deleteAllEvents(){
        try{
            eventRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/events/published")
    public ResponseEntity<List<Event>> findByPublished(){
        try{
            List<Event> events = eventRepository.findByPublished(true);

            if(events.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(events, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
