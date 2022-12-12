package com.cognizant.authmicroservice.repository;

import com.cognizant.authmicroservice.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findById(int userid);


}
