package com.sia.manager.repository;

import com.sia.manager.models.ERole;
import com.sia.manager.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository  extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
