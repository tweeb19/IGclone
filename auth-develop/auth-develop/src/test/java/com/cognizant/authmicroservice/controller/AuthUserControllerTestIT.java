package com.cognizant.authmicroservice.controller;

import com.cognizant.authmicroservice.AuthMicroserviceApplication;
import com.cognizant.authmicroservice.config.JWTAuthenticationEntryPoint;
import com.cognizant.authmicroservice.services.AuthUserServiceImpl;
import com.cognizant.authmicroservice.services.JWTTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;




@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AuthMicroserviceApplication.class)
@AutoConfigureMockMvc
public class AuthUserControllerTestIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JWTTokenService jwtTokenService;


    @Test
    void test_status_ok() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/authenticate").content("{\"username\": \"user1\", \"password\": \"pass1\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.token", CoreMatchers.notNullValue()));

    }

    @Test
    void test_wrong_username() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/authenticate").content("{\"username\": \"user11\", \"password\": \"pass1\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void test_wrong_password() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/authenticate").content("{\"username\": \"user1\", \"password\": \"pass11\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void test_validate() throws Exception {
        jwtTokenService.setSecret("mock_secret");
        String valid_token = Jwts.builder()
                .setSubject("user1")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, "mock_secret")
                .compact();
        String date_invalid_token = Jwts.builder()
                .setSubject("user1")
                .setExpiration(new Date(System.currentTimeMillis()-1000*60*60*24))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, "mock_secret")
                .compact();
        String sign_invalid_token = Jwts.builder()
                .setSubject("user1")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, "smock_secret")
                .compact();
        mvc.perform(MockMvcRequestBuilders.get("/validate?token=" + valid_token + "&username=user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(true)));
        mvc.perform(MockMvcRequestBuilders.get("/validate?token=" + valid_token + "&username=user2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(false)));
        mvc.perform(MockMvcRequestBuilders.get("/validate?token=" + date_invalid_token + "&username=user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(false)));
        mvc.perform(MockMvcRequestBuilders.get("/validate?token=" + sign_invalid_token + "&username=user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(false)));
    }

}
