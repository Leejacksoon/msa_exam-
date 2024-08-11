package com.sparta.msa_exam.gateway.repository;


import com.sparta.msa_exam.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);

}
