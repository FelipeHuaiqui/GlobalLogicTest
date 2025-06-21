package com.globallogic.globallogic.repository;

import com.globallogic.globallogic.repository.model.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserRegister, UUID> {
    Optional<UserRegister> findByEmail(String email);
    Optional<UserRegister> findByToken(String token);
}
