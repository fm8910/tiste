package com.ni.tiste;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ni.tiste.controllers.UserController;
import com.ni.tiste.model.User;
import com.ni.tiste.payload.PhoneDTO;
import com.ni.tiste.payload.SignupRequest;
import com.ni.tiste.payload.UserResponse;
import com.ni.tiste.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAll() throws Exception {
        UserResponse userResponse = new UserResponse(UUID.randomUUID(),"pepito perez",
                "pepito@gmail.com", LocalDateTime.now(), LocalDateTime.now(),"",true);

        List<UserResponse> userResponseList = List.of(userResponse);

        Mockito.when(userService.getAllUsers()).thenReturn(userResponseList);

        mockMvc.perform(get("/user")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", Matchers.hasSize(1))).
                andExpect(jsonPath("$[0].name", Matchers.is("pepito perez")));
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.org");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setPassword("");
        user.setToken("");
        user.setIsActive(true);

        Mockito.when(userService.saveUser(Mockito.any(SignupRequest.class))).thenReturn(user);
        PhoneDTO phoneDTO = new PhoneDTO("1234567","1","57");
        SignupRequest signupRequest = new SignupRequest("Juan Rodriguez","juan@rodriguez.org",
                "Hunter2!",List.of(phoneDTO));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/user")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", Matchers.is("Juan Rodriguez"))).
                andExpect(jsonPath("$.email", Matchers.is("juan@rodriguez.org")));
    }

}
