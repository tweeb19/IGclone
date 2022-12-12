package com.cognizant.authmicroservice.services;

import com.cognizant.authmicroservice.models.AuthUser;
import com.cognizant.authmicroservice.repository.AuthRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthUserServiceImplTest {

    AuthUserServiceImpl authUserService;

    @Mock
    AuthRepository repository;

    @BeforeEach
    public void init(){
        repository = Mockito.mock(AuthRepository.class);
        authUserService = new AuthUserServiceImpl();
        authUserService.setAuthRepository(repository);
    }

    @Test
    void test_user_return() {
        Mockito.when(repository.findByUsername("test"))
                .thenReturn(Optional.of(new AuthUser(1, "test", "test")));

        UserDetails userDetails = authUserService.loadUserByUsername("test");
        assertEquals(userDetails.getUsername(), "test");


    }

    @Test
    void test_empty_user(){
        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authUserService.loadUserByUsername("test"));


    }

    @Test
    void save(){
        AuthUser authUser = new AuthUser(1, "test1", "test");
        Mockito.when(repository.findByUsername("test1")).thenReturn(Optional.of(new AuthUser()));
        assertNull(authUserService.save(authUser));
        Mockito.when(repository.findByUsername("test1")).thenReturn(Optional.empty());
        Mockito.when(repository.save(authUser)).thenReturn(authUser);
        assertEquals(authUserService.save(authUser), authUser);
    }
}