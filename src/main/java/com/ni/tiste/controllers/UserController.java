package com.ni.tiste.controllers;

import com.ni.tiste.model.User;
import com.ni.tiste.payload.SignupRequest;
import com.ni.tiste.payload.UserResponse;
import com.ni.tiste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userService.saveUser(signUpRequest);
        return ResponseEntity.ok(new UserResponse(user.getId(),
                user.getName(),user.getEmail(),user.getCreated(),
                user.getLastLogin(),user.getToken(),user.getIsActive()));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "email") String email,
                                        @Valid @RequestBody SignupRequest signUpRequest) {
        User user = userService.createOrReplaceUser(email,signUpRequest);
        return  ResponseEntity.ok(new UserResponse(user.getId(),
                user.getName(),user.getEmail(),user.getCreated(),
                user.getLastLogin(),user.getToken(),user.getIsActive()));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

}
