package com.cognizant.authmicroservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class JWTTokenServiceTest {

    String testToken;

    Date test_expiration_date;
    Date test_issue_date;

    private String mock_secret = "test";
    JWTTokenService jwtTokenService;

    @BeforeEach
    public void init(){
        test_issue_date = new Date();
        test_expiration_date = new Date(System.currentTimeMillis() + 1000*60*60*24);
        jwtTokenService = new JWTTokenService();
        jwtTokenService.setSecret(mock_secret);
        testToken = Jwts.builder()
                .setSubject("user1")
                .setExpiration(test_expiration_date)
                .setIssuedAt(test_issue_date)
                .signWith(SignatureAlgorithm.HS512, mock_secret)
                .compact();
    }

    @Test
    void getUsernameFromToken() {
        assertEquals(jwtTokenService.getUsernameFromToken(testToken), "user1");
    }

    @Test
    void getExpirationDateFromToken() {
        //Jwts.builder() sets expiration date to nearest second not nearest millisecond.
        assertEquals(jwtTokenService.getExpirationDateFromToken(testToken).getTime() / 1000, test_expiration_date.getTime() / 1000);
    }

    @Test
    void getClaimsFromToken() {
        assertEquals(jwtTokenService.getClaimsFromToken(testToken, Claims::getSubject), "user1");
        assertEquals(jwtTokenService.getClaimsFromToken(testToken, Claims::getExpiration).getTime() / 1000, test_expiration_date.getTime() / 1000);
    }

    @Test
    void generateToken() {
        UserDetails userDetails = new User("test_user", "null", new ArrayList<>());

        String token = jwtTokenService.generateToken(userDetails);

        Claims claims = Jwts.parser().setSigningKey(mock_secret).parseClaimsJws(token).getBody();
        String username = ((Function<Claims, String>)(Claims::getSubject)).apply(claims);
        Date expiration = ((Function<Claims, Date>)(Claims::getExpiration)).apply(claims);
        Date issue = ((Function<Claims, Date>)(Claims::getIssuedAt)).apply(claims);

        assertEquals("test_user", username);
        assertTrue(expiration.after(new Date()));
        assertTrue(issue.before(new Date()));
    }

    @Test
    void validateToken() {
        UserDetails userDetails = new User("user1", "null", new ArrayList<>());
        UserDetails userDetails_inv = new User("user11", "null", new ArrayList<>());

        String date_exp_token = Jwts.builder()
                .setSubject("user1")
                .setExpiration(new Date(System.currentTimeMillis()-1000*60*60*24))
                .setIssuedAt(new Date(1651712203000L))
                .signWith(SignatureAlgorithm.HS512, mock_secret)
                .compact();

        String date_valid_token = Jwts.builder()
                .setSubject("user1")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .setIssuedAt(new Date(1651712203000L))
                .signWith(SignatureAlgorithm.HS512, mock_secret)
                .compact();

        boolean user_inv = jwtTokenService.validateToken(date_valid_token, userDetails_inv);
        boolean valid = jwtTokenService.validateToken(date_valid_token, userDetails);

        assertThrows(ExpiredJwtException.class, () -> jwtTokenService.validateToken(date_exp_token, userDetails_inv));
        assertThrows(ExpiredJwtException.class, () -> jwtTokenService.validateToken(date_exp_token, userDetails));
        assertFalse(user_inv);
        assertTrue(valid);
    }
}