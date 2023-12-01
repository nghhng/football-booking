package org.example.controllers;

import org.example.dao.Booking;
import org.example.dao.MatchingRequest;
import org.example.dto.CreateCommentRequest;
import org.example.dto.MatchingRestRequest;
import org.example.dto.ReponseMatchRequest;
import org.example.services.BookingService;
import org.example.services.CommentService;
import org.example.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tunght.toby.common.entity.Comment;
import tunght.toby.common.security.AuthUserDetails;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest createCommentRequest, @AuthenticationPrincipal AuthUserDetails authUserDetails){
        return new ResponseEntity<Comment>(commentService.createComment(createCommentRequest, authUserDetails), HttpStatus.OK);
    }
}
