package com.sunpark.ticketflow.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "verification")
public class VerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    private String phone;

    private boolean verify;

    private String code;

    private LocalDateTime verify_at;

    @Builder
    public VerificationEntity(Integer id, String userId, String phone, boolean verify, String code) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.verify = verify;
        this.code = code;
        this.verify_at = LocalDateTime.now();
    }
}
