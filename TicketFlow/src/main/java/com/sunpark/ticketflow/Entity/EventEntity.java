package com.sunpark.ticketflow.Entity;

import com.sunpark.ticketflow.DTO.EventDTO;
import com.sunpark.ticketflow.Enum.EventStatus;
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
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String category;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private LocalDateTime booking_start_at;

    private LocalDateTime booking_end_at;

    private LocalDateTime created_at;

    @Builder
    public EventEntity(String title, String description, String category, EventStatus status, LocalDateTime booking_start_at, LocalDateTime booking_end_at) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.booking_start_at = booking_start_at;
        this.booking_end_at = booking_end_at;
        this.created_at = LocalDateTime.now();
    }
}
