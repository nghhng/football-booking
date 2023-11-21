package org.example.repositories;


import org.bson.types.ObjectId;
import org.example.dao.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {
    Booking[] findByUserId(String userId);

    Booking findById(String id);

    Booking[] findByFacilityIdAndFieldIndexAndAndDateAndPriceIdAndHasOpponent(String facilityId, String fieldIndex, String date, String priceId, String hasOpponent);
}
