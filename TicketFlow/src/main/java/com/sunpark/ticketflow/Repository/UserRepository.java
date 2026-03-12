package com.sunpark.ticketflow.Repository;

import com.sunpark.ticketflow.Entity.UserEntity;
import com.sunpark.ticketflow.Enum.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    boolean existsByUserId(String userId);
    boolean existsByUserIdAndRole(String userId, UserRole role);
    UserEntity findByUserId(String userId);
}

