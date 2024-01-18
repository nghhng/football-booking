package org.example.services;


import lombok.RequiredArgsConstructor;
import org.example.access.FacilityFeignClient;
import org.example.access.UserFeignClient;
import org.example.access.model.*;
import org.example.dao.Facility;
import org.example.dao.User;
import org.example.dto.*;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.example.repositories.FacilityRepository;
import org.example.repositories.MatchingRequestRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import nghhng.common.entity.Comment;
import nghhng.common.security.AuthUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Value(value = "${kafka.booking-noti-topic}")
    private String bookingNotiTopic;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MatchingRequestRepository matchingRequestRepository;

    @Autowired
    FacilityFeignClient facilityFeignClient;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    MongoTemplate mongoTemplate;

    private final KafkaTemplate<String, Object> notiKafkaTemplate;


    public Comment createComment(CreateCommentRequest createCommentRequest, AuthUserDetails authUserDetails) {
        if(createCommentRequest.getTargetType().equals("facility")) {
            return processFacilityComment(createCommentRequest, authUserDetails);
        } else if (createCommentRequest.getTargetType().equals("user")){
            return processUserComment(createCommentRequest, authUserDetails);
        } else{
            throw new BaseException("Target type not exists");
        }
    }

    private Comment processFacilityComment(CreateCommentRequest createCommentRequest, AuthUserDetails authUserDetails){
        Facility target = getFacilityTarget(createCommentRequest);
        Comment comment = Comment.builder()
                .authorId(authUserDetails.getId())
                .body(createCommentRequest.getBody())
                .rating(createCommentRequest.getRating())
                .build();
        if(target.getComments()==null) {
            List<Comment> comments = new ArrayList<Comment>();
            target.setComments(comments);
        }
        target.getComments().add(comment);

        Double newRating = calculateAverageRating(target.getComments());
        target.setRating(newRating);

        target = saveFacilityTarget(target);
        return comment;
    }

    private Comment processUserComment(CreateCommentRequest createCommentRequest, AuthUserDetails authUserDetails){
        User target = getUserTarget(createCommentRequest);
        Comment comment = Comment.builder()
                .authorId(authUserDetails.getId())
                .body(createCommentRequest.getBody())
                .rating(createCommentRequest.getRating())
                .build();
        if(target.getComments()==null) {
            List<Comment> comments = new ArrayList<Comment>();
            target.setComments(comments);
        }
        target.getComments().add(comment);

        Double newRating = calculateAverageRating(target.getComments());
        target.setRating(newRating);

        target = saveUserTarget(target);
        return comment;
    }

    private Facility getFacilityTarget(CreateCommentRequest createCommentRequest) {
        GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest = new GetFacilityByFacilityIdRequest(createCommentRequest.getTargetId());
        Facility target = facilityFeignClient.getFacilityById(getFacilityByFacilityIdRequest);
        if (target == null) {
            throw new BaseException("Facility not exists");
        }
        return target;
    }
    private User getUserTarget(CreateCommentRequest createCommentRequest) {
        GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest(createCommentRequest.getTargetId());
        User target = userFeignClient.getUserById(getUserByIdRequest);
        if (target == null) {
            throw new BaseException("User not exists");
        }
        return target;
    }
    private Facility saveFacilityTarget(Facility target) {
        Facility facility = facilityRepository.save(target);
        return facility;
    }

    private User saveUserTarget(User target) {
        User user = userRepository.save(target);
        return user;
    }
    private static double calculateAverageRating(List<Comment> comments) {
        // Ensure the list is not empty
        if (comments.isEmpty()) {
            return 0.0; // or throw an exception if needed
        }

        // Calculate the sum of ratings using Java Stream
        double sumOfRatings = comments.stream()
                .mapToDouble(Comment::getRating)
                .sum();

        // Calculate the average and round to 1 decimal place
        return Math.round((sumOfRatings / comments.size()) * 10.0) / 10.0;
    }
}
