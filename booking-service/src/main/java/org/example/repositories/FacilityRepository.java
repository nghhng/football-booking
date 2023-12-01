package org.example.repositories;
import org.bson.types.ObjectId;
import org.example.dao.Facility;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacilityRepository extends MongoRepository<Facility, ObjectId> {
    Facility[] findByOwnerId(String ownerId);

    Facility findById(String facilityId);
}
