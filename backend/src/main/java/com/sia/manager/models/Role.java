package com.sia.manager.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@Document(collection = "roles")
public class Role {
    @Id
    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private ERole name;
    public Role() {

    }
    public Role(ERole name){
        this.name = name;
    }

}
