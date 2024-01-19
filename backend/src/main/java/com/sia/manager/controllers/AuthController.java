package com.sia.manager.controllers;

import com.sia.manager.models.ERole;
import com.sia.manager.models.Role;
import com.sia.manager.models.User;
import com.sia.manager.payload.request.LoginRequest;
import com.sia.manager.payload.request.SignupRequest;
import com.sia.manager.payload.response.JwtResponse;
import com.sia.manager.payload.response.MessageResponse;
import com.sia.manager.repository.RoleRepository;
import com.sia.manager.repository.UserRepository;
import com.sia.manager.security.jwt.AuthEntryPointJwt;
import com.sia.manager.security.jwt.JwtUtils;
import com.sia.manager.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){

        logger.info("Checking if username '{}' already exists...", signupRequest.getUsername());
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(signupRequest.getUsername(),signupRequest.getEmail(),encoder.encode(signupRequest.getPassword()));

        Set<String> sRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(sRoles == null){
            ERole userRoleName = ERole.ROLE_USER;
            logger.info("Searching for role with name: {}", userRoleName);

            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()-> new RuntimeException("Error: Role is not found."));

            logger.info(String.valueOf(userRole.getName()));
            roles.add(userRole);
        }else {
            sRoles.forEach(role-> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
