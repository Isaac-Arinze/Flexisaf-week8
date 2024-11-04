package com.flexisaf.week8.repository;

import com.flexisaf.week8.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
Optional <User> findByEmail(String email);
}
