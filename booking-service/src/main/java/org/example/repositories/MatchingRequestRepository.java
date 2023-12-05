package org.example.repositories;

import org.bson.types.ObjectId;
import org.example.dao.MatchingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchingRequestRepository extends MongoRepository<MatchingRequest, ObjectId> {

    MatchingRequest findMatchingRequestById(String id);

    List<MatchingRequest> findMatchingRequestByBookingId(String bookingId);

    List<MatchingRequest> deleteMatchingRequestById(String id);
}
