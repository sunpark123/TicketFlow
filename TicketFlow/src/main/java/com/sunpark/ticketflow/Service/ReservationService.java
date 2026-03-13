package com.sunpark.ticketflow.Service;


import com.sunpark.ticketflow.DTO.EventDTO;
import com.sunpark.ticketflow.Entity.EventEntity;
import com.sunpark.ticketflow.Entity.ReservationEntity;
import com.sunpark.ticketflow.Entity.SeatsEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.EventStatus;
import com.sunpark.ticketflow.Enum.SeatStatus;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Repository.EventRepository;
import com.sunpark.ticketflow.Repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RedisService redisService;
    private final SeatsService seatsService;
    private final EventService eventService;

    public ReservationEntity reservationTry(Integer eventId, Integer seatNumber, String userId) {
        boolean locked = redisService.reservationTry(
                eventId,
                seatNumber,
                userId
        );
        if(!locked) {
            throw new CustomException(ErrorCode.ALREADY_RESERVATION);
        }

        eventService.getEventOrThrow(eventId);
        SeatsEntity seatEntity = seatsService.getSeatOrThrow(seatNumber);
        if(!seatEntity.getStatus().equals(SeatStatus.AVAILABLE)) {
            throw new CustomException(ErrorCode.ALREADY_RESERVATION);
        }

        ReservationEntity reservationEntity = ReservationEntity.builder()
                .eventId(eventId)
                .seatNumber(seatNumber)
                .status(SeatStatus.WAITING)
                .build();

        seatEntity.setStatus(SeatStatus.WAITING);
        seatsService.save(seatEntity);

        return reservationRepository.save(reservationEntity);
    }

}
