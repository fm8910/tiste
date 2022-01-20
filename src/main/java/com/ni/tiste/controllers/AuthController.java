package com.ni.tiste.controllers;

import com.ni.tiste.model.User;
import com.ni.tiste.payload.SignupRequest;
import com.ni.tiste.payload.UserInfoResponse;
import com.ni.tiste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userService.saveUser(signUpRequest);
        return ResponseEntity.ok(new UserInfoResponse(user.getId(),
                user.getEmail(),user.getCreated(),
                user.getLastLogin(),user.getToken(),user.getIsActive()));
    }



}
