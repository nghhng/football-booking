package tunght.toby.auth.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.EStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    List<UserEntity> findAllByUsernameAndStatus(String username, EStatus status);
    List<UserEntity> findAllByEmailAndStatus(String email, EStatus status);
    List<UserEntity> findAllByUsername(String username);
    List<UserEntity> findAllByEmail(String email);

//    @Query(value = "SELECT * FROM users WHERE email = :email " +
//            "ORDER BY created_at DESC " +
//            "LIMIT 1", nativeQuery = true)
    Optional<UserEntity> findLatestCreatedAccountByEmail(String email);
}
