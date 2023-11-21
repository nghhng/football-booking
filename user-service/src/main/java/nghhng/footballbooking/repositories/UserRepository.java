package nghhng.footballbooking.repositories;

import nghhng.footballbooking.dao.UserDAO;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDAO, ObjectId> {
    UserDAO findByUsername(String username);

    Optional<UserDAO> findById(String id);
}
