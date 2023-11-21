package org.example.repositories;

import org.bson.types.ObjectId;
import org.example.dao.MatchingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchingRequestRepository extends MongoRepository<MatchingRequest, ObjectId> {

    MatchingRequest findMatchingRequestById(String id);
}
