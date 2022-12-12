package com.cognizant.authmicroservice.controller;


import com.cognizant.authmicroservice.models.AuthUser;
import com.cognizant.authmicroservice.models.JWTRequest;
import com.cognizant.authmicroservice.models.JWTResponse;
import com.cognizant.authmicroservice.services.AuthUserServiceImpl;
import com.cognizant.authmicroservice.services.JWTTokenService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.security.config.Elements.JWT;

@RestController
@CrossOrigin(origins = "${ui.url}", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "Authorization")
public class AuthUserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenService jwtTokenService;

    @Autowired
    AuthUserServiceImpl authUserServiceImpl;

    public void setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    public void setJwtTokenService(JWTTokenService jwtTokenService){
        this.jwtTokenService = jwtTokenService;
    }

    public void setAuthUserServiceImpl(AuthUserServiceImpl authUserService){
        this.authUserServiceImpl = authUserService;
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token, @RequestParam String username){
        try {
            boolean valid = jwtTokenService.validateToken(token, new User(username, "null", new ArrayList<>()));
            return ResponseEntity.ok(valid);
        }
        catch (JwtException e){
            return ResponseEntity.ok(false);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> responseEntity(@RequestBody JWTRequest authRequest)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = authUserServiceImpl.loadUserByUsername(authRequest.getUsername());

        final String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }
    @PostMapping("/user")
    public ResponseEntity<?> saveAuthUser(@RequestBody JWTRequest authRequest) {


        AuthUser authUser =

        authUserServiceImpl.save(new AuthUser(0, authRequest.getUsername(), authRequest.getPassword()));
        if (authUser == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       UserDetails userDetails= authUserServiceImpl.loadUserByUsername(authRequest.getUsername());
       String tokens = jwtTokenService.generateToken(userDetails);
       //if (tokens != null)

//verifies and return jwt
        return ResponseEntity.ok(new JWTResponse(tokens));

    }
}
