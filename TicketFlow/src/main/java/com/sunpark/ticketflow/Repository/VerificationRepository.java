package com.sunpark.ticketflow.Repository;

import com.sunpark.ticketflow.Entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Integer>{
    boolean existsByUserIdAndPhoneAndVerifyTrue(String userId, String phone);
    boolean existsByUserIdAndVerifyTrue(String userId);
    boolean existsByPhoneAndVerifyTrue(String phone);
    Optional<VerificationEntity> findByUserId(String userId);

    @Query("""
        SELECT 
            CASE 
                WHEN COUNT(v) > 0 THEN 0
                WHEN EXISTS (
                    SELECT 1 FROM VerificationEntity v2 
                    WHERE v2.userId = :userId 
                    AND v2.phone = :phone
                ) THEN 1
                ELSE 2
            END
        FROM VerificationEntity v
        WHERE v.userId = :userId
        AND v.phone = :phone
        AND v.code = :code
    """)
    int checkVerification(String userId, String phone, String code);
}

