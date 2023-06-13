package org.example.repositories;


import org.bson.types.ObjectId;
import org.example.dao.Booking;
import org.example.dao.part.Field;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {
    Booking[] findByUserId(ObjectId userId);

    Booking[] findByFacilityIdAndFieldAndDateAndPriceId(ObjectId facilityId, Field field, String date, ObjectId priceId);
}
