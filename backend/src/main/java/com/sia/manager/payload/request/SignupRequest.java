package com.sia.manager.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 6, max = 30)
    @NotBlank
    private String password;

    @Size(max = 50)
    @NotBlank
    @Email
    private String email;

    private Set<String> roles;
}
