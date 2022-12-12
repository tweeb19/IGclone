package com.cognizant.authmicroservice.controller;

import com.cognizant.authmicroservice.models.AuthUser;
import com.cognizant.authmicroservice.models.JWTRequest;
import com.cognizant.authmicroservice.models.JWTResponse;
import com.cognizant.authmicroservice.services.AuthUserServiceImpl;
import com.cognizant.authmicroservice.services.JWTTokenService;
import com.sun.istack.NotNull;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.bouncycastle.asn1.iana.IANAObjectIdentifiers.security;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
import static shaded.org.apache.maven.wagon.PathUtils.user;

class AuthUserControllerTest {
    private MockHttpServletRequestBuilder mvc;
    private AuthUser authUser;
    AuthUserController authUserController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    JWTTokenService jwtTokenService;

    @Mock
    AuthUserServiceImpl authUserServiceImpl;

    @BeforeEach
    public void init(){
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        jwtTokenService = Mockito.mock(JWTTokenService.class);
        authUserServiceImpl = Mockito.mock(AuthUserServiceImpl.class);
        authUserController = new AuthUserController();
        authUserController.setAuthUserServiceImpl(authUserServiceImpl);
        authUserController.setAuthenticationManager(authenticationManager);
        authUserController.setJwtTokenService(jwtTokenService);
    }

    @Test
    void test_authentication_manager_called() {
        JWTRequest jwtRequest = new JWTRequest();
        jwtRequest.setPassword("pass");
        jwtRequest.setUsername("user");
        authUserController.responseEntity(jwtRequest);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        Mockito.verify(authenticationManager).authenticate(captor.capture());
        UsernamePasswordAuthenticationToken token = captor.getValue();
        assertEquals(token.getPrincipal(), "user");
        assertEquals(token.getCredentials(), "pass");
    }

    @Test
    void test_auth_user_service_called(){
        JWTRequest jwtRequest = new JWTRequest();
        jwtRequest.setPassword("pass");
        jwtRequest.setUsername("user");
        User user = new User("user", "null", new ArrayList<>());
        Mockito.when(authUserServiceImpl.loadUserByUsername("user"))
                .thenReturn(user);
        authUserController.responseEntity(jwtRequest);
        Mockito.verify(authUserServiceImpl).loadUserByUsername("user");
        Mockito.verify(jwtTokenService).generateToken(user);
    }

    @Test
    void test_jwt_token_return(){
        Mockito.when(jwtTokenService.generateToken(Mockito.any()))
                .thenReturn("test_token");
        ResponseEntity entity = authUserController.responseEntity(new JWTRequest());
        assertEquals(((JWTResponse)entity.getBody()).getToken(), "test_token");
    }
    @Test
    @NotNull
    void testsavedAuthUser()throws Exception{
         authUser = new AuthUser(1,"user","password");
        mvc = MockMvcRequestBuilders.
                        post("/user").accept(MediaType.ALL);
//                .andExpect(status().isOk())
//                .andExpect(content().string("New User Saved"));
//        Mockito.when(authUserServiceImpl.loadUserByUsername("")).thenReturn((UserDetails) authUser);
//        assertNotNull(authUserController.saveAuthUser(new JWTRequest()));



    }
    @Test
    @Nullable
    void testnullsavedAuthUser() throws Exception {

        mvc= MockMvcRequestBuilders
                        .post("/user").accept(MediaType.ALL);
//                .andExpect(status().isBadRequest())
//       .andExpect(content().string("New User not Saved"));


    }

    @Test
    void test_valid_token(){
        Mockito.when(jwtTokenService.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(false);
        assertFalse((Boolean) authUserController.validate("test", "test").getBody());
        Mockito.when(jwtTokenService.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenReturn(true);
        assertTrue((Boolean) authUserController.validate("test", "test").getBody());
        Mockito.when(jwtTokenService.validateToken(Mockito.anyString(), Mockito.any(UserDetails.class))).thenThrow(MalformedJwtException.class);
        assertFalse((Boolean) authUserController.validate("test2", "test").getBody());
    }


}