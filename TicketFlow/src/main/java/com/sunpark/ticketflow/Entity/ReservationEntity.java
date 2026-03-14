package com.sunpark.ticketflow.Entity;

import com.sunpark.ticketflow.Enum.SeatStatus;
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
@Table(name = "reservation_seat")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "user_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private LocalDateTime reservation_at;

    @Builder
    public ReservationEntity(Integer eventId, Integer seatNumber, String userId, SeatStatus status) {
        this.eventId = eventId;
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.status = status;
        this.reservation_at = LocalDateTime.now();
    }
}
