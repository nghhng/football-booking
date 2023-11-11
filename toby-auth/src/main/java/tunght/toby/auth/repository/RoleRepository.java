package tunght.toby.auth.repository;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tunght.toby.common.entity.RoleEntity;
import tunght.toby.common.enums.ERole;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, ObjectId> {
    RoleEntity findByRole(ERole role);
}
