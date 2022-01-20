package com.ni.tiste.service;

import com.ni.tiste.config.JwtUtils;
import com.ni.tiste.model.User;
import com.ni.tiste.payload.SignupRequest;
import com.ni.tiste.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    public User saveUser(SignupRequest userRequest){
        String token= jwtUtils.generateTokenFromUser(userRequest.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setToken(token);
        user.setLastLogin(LocalDateTime.now());
        user.setEmail(userRequest.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setIsActive(true);

      return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }



}
