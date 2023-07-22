package nghhng.facilityservice.repositories;

import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dao.Price;
import nghhng.facilityservice.dao.part.Time;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends MongoRepository<Price, ObjectId> {

    Price[] getPriceByFacilityId(ObjectId facilityId);


    @Query("{ $and: [ { 'facilityId': ?0 }, { 'fieldType': ?1 }, { 'startAt': ?2 }, { 'endAt': ?3 }, { 'amount': ?4 }, { 'isWeekend': ?5 } ] }")
    List<Price> findByFields(ObjectId facilityId, String fieldType, Time startAt, Time endAt, int amount, String isWeekend);

    List<Price> findAll();

    List<Price> findByFacilityIdAndFieldTypeAndStartAtAndEndAtAndAmountAndSpecialAmount(ObjectId facilityId, String fieldType, Time startAt, Time endAt, int amount, int specialAmount);
}
