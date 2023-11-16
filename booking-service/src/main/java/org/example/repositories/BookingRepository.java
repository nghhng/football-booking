package org.example.repositories;


import org.bson.types.ObjectId;
import org.example.dao.Booking;
import org.example.dao.part.BookField;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {
    Booking[] findByUserId(ObjectId userId);

    Booking[] findByFacilityIdAndFieldIndexAndAndDateAndPriceIdAndHasOpponent(ObjectId facilityId, String fieldIndex, String date, ObjectId priceId, String hasOpponent);
}
