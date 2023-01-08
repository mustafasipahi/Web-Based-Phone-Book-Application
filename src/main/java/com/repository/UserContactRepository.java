package com.repository;

import com.entity.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserContactRepository extends JpaRepository<UserContactEntity, Long> {

    @Query("SELECT uc " +
           "FROM UserContactEntity uc " +
           "WHERE uc.user.id = :userId")
    Optional<UserContactEntity> findByUserId(@Param("userId") Long userId);
}
