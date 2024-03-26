package com.flux.uvew.repository;

import com.flux.uvew.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<User, String> {
    Optional<User> findBySub(String sub);
}
