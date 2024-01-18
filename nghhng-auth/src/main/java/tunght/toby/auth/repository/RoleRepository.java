package tunght.toby.auth.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import nghhng.common.entity.RoleEntity;
import nghhng.common.enums.ERole;

@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, ObjectId> {
    RoleEntity findByRole(ERole role);
}
