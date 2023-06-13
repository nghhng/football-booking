package nghhng.facilityservice.repositories;

import nghhng.facilityservice.dao.Price;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, ObjectId> {

    Price[] getPriceByFacilityId(ObjectId facilityId);
}
