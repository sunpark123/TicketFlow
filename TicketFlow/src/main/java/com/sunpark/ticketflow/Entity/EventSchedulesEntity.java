package com.sunpark.ticketflow.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event_schedules")
public class EventSchedulesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_id")
    private Integer eventId;

    private LocalDateTime start_at;

    private LocalDateTime end_at;

    @Column(name = "total_seat")
    private Integer totalSeat;

    private LocalDateTime created_at;
}
