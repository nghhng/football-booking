package nghhng.footballbooking.repositories;

import nghhng.footballbooking.dao.UserDAO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends MongoRepository<UserDAO, ObjectId> {
    UserDAO findByUsername(String username);
}
