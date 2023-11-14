package nghhng.footballbooking.controllers;


import nghhng.footballbooking.dao.UserDAO;
import nghhng.footballbooking.dto.CreateUserRequest;
import nghhng.footballbooking.dto.GetUserByUsernameRequest;
import nghhng.footballbooking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tunght.toby.common.security.AuthUserDetails;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDAO>> getAllUsers(){
        return new ResponseEntity<List<UserDAO>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDAO> createUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<UserDAO>(userService.createUser(createUserRequest), HttpStatus.OK);
    }

    @PostMapping("getByUsername")
    public ResponseEntity<UserDAO> getUserByUsername(@RequestBody  GetUserByUsernameRequest getUserByUsernameRequest){
        return new ResponseEntity<UserDAO>(userService.getUserByUsername(getUserByUsernameRequest.getUsername()), HttpStatus.OK);
    }

    @GetMapping("current")
    public ResponseEntity<UserDAO> currentUser(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return new ResponseEntity<UserDAO>(userService.currentUser(authUserDetails),HttpStatus.OK);
    }
}
