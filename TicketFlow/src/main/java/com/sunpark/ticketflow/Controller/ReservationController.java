package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.DTO.ReservationDTO;
import com.sunpark.ticketflow.Entity.ReservationEntity;
import com.sunpark.ticketflow.Entity.SeatsEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.SeatStatus;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Service.EventService;
import com.sunpark.ticketflow.Service.RedisService;
import com.sunpark.ticketflow.Service.ReservationService;
import com.sunpark.ticketflow.Service.SeatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/select")
    public ResponseEntity<?> selectSeats(@Valid @RequestBody ReservationDTO reservationDTO, @RequestHeader("X-User-Id") String userId) {
        Integer eventId = reservationDTO.getEventId();
        Integer seatId = reservationDTO.getSeatNumber();

        ReservationEntity reservationEntity = reservationService.reservationTry(eventId, seatId, userId);

        return ResponseEntity.ok("예약 성공!! : " + reservationEntity.getEventId() + " " + reservationEntity.getSeatNumber());
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmSeat(@Valid @RequestBody ReservationDTO reservationDTO, @RequestHeader("X-User-Id") String userId) {
        reservationDTO.setUserId(userId);
        reservationService.reservationConfirm(reservationDTO);

        return ResponseEntity.ok("good");
    }
}
