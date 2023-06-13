package nghhng.facilityservice.repositories;

import nghhng.facilityservice.dao.Facility;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacilityRepository extends MongoRepository<Facility, ObjectId> {
    Facility[] findByOwnerId(ObjectId ownerId);

    Facility findBy_id(ObjectId facilityId);
}
