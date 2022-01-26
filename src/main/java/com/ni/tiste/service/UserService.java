package com.ni.tiste.service;

import com.ni.tiste.config.JwtUtils;
import com.ni.tiste.model.User;
import com.ni.tiste.payload.SignupRequest;
import com.ni.tiste.payload.UserResponse;
import com.ni.tiste.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Transactional
    public User saveUser(SignupRequest userRequest){
        String token= jwtUtils.generateTokenFromUser(userRequest.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(userRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setToken(token);
        user.setLastLogin(LocalDateTime.now());
        user.setEmail(userRequest.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setIsActive(true);

       return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createOrReplaceUser(String email, SignupRequest userRequest){
       return userRepository.findByEmail(email)
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setModified(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    userRequest.setEmail(email);
                    return this.saveUser(userRequest);
                });
    }

    public void deleteUserByEmail(String email){
        userRepository.deleteByEmail(email);
    }

    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.
                stream()
                .map(user -> new UserResponse(user.getId(),user.getName(),user.getEmail(),
                user.getCreated(),user.getLastLogin(),user.getToken(),user.getIsActive())).
                collect(Collectors.toList());
    }


}
