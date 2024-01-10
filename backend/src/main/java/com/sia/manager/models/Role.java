package com.sia.manager.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private ERole name;
    public Role() {

    }
    public Role(ERole name){
        this.name = name;
    }

}
