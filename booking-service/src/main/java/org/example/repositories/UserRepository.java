package org.example.repositories;

import org.bson.types.ObjectId;
import org.example.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    Optional<User> findById(String id);
}
