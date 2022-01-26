package com.ni.tiste;

import com.ni.tiste.config.JwtUtils;
import com.ni.tiste.model.User;
import com.ni.tiste.payload.PhoneDTO;
import com.ni.tiste.payload.SignupRequest;
import com.ni.tiste.repository.UserRepository;
import com.ni.tiste.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveUser() {
        PhoneDTO phoneDTO = new PhoneDTO("1234567","1","57");
        SignupRequest signupRequest = new SignupRequest("Juan Rodriguez","juan@rodriguez.org",
                "hunter2", List.of(phoneDTO));
        userService.saveUser(signupRequest);
        // verify if the save method is called when saveUser is called too
        verify(userRepository, times(1)).save(any(User.class));
    }


}
