package org.example.controllers;

import org.example.dto.CreateCommentRequest;
import org.example.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nghhng.common.entity.Comment;
import nghhng.common.security.AuthUserDetails;

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
