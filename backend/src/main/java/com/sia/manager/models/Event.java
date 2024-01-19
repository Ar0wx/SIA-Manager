package com.sia.manager.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {
    @Id
    @Getter
    private String id;
    @Getter
    @Setter
    private String eventName;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private boolean published;

    public Event(){

    }

    public Event(String eventName, String description, boolean published){
        this.eventName =  eventName;
        this.description = description;
        this.published = published;
    }
    @Override
    public String toString(){
        return "Tutorial [id=" + id +", eventName=" + eventName + ", desc=" +description + ", published" +  published +"]";
    }
}
