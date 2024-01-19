package com.sia.manager.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;



@Document(collection = "users")
public class User {
    @Id
    @Setter
    @Getter
    private String id;
    @NotBlank
    @Size(max = 20)
    @Setter
    @Getter
    private String username;
    @NotBlank
    @Size(max = 50)
    @Setter
    @Getter
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
