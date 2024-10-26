package com.github.guilhermebauer.studymanagement.repository;


import com.github.guilhermebauer.studymanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u WHERE u.email =:email")
    Optional<UserEntity> findUserByEmail(@Param("email") String email);
}
