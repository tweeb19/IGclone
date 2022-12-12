package com.cognizant.authmicroservice.services;


import com.cognizant.authmicroservice.models.AuthUser;
import com.cognizant.authmicroservice.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthUserServiceImpl implements AuthUserService, UserDetailsService {

    @Autowired
    AuthRepository authRepository;

    public void setAuthRepository(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authRepository.findByUsername(username).orElse(null);
        if(authUser == null)
        {
            throw new UsernameNotFoundException("User Not Found");
        }
        UserDetails user = new User(authUser.getUsername(), authUser.getPassword(), new ArrayList<>());

        return user;
    }

    public AuthUser save(AuthUser authUser){
        if(authRepository.findByUsername(authUser.getUsername()).isPresent()){
            return null;
        }
        return authRepository.save(authUser);
    }
}
