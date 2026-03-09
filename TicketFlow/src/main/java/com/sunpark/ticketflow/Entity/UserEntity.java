package com.sunpark.ticketflow.Entity;

import com.sunpark.ticketflow.Enum.UserRole;
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
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    private String password;

    private String name;

    private String phone;

    private UserRole role;

    private LocalDateTime created_at;

    @Builder
    public UserEntity(String userId, String password, String name, String phone, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.created_at = LocalDateTime.now();
    }
}
