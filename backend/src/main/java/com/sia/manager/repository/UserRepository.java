package com.sia.manager.repository;

import com.sia.manager.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserBy (String username);

    Boolean existsUserBy(String username);
    Boolean existsEmailBy(String email);
}
