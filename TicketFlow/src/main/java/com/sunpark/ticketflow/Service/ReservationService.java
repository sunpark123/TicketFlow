package com.sunpark.ticketflow.Service;


import com.sunpark.ticketflow.DAO.ReservationDAO;
import com.sunpark.ticketflow.DAO.SeatsDAO;
import com.sunpark.ticketflow.DTO.EventDTO;
import com.sunpark.ticketflow.DTO.ReservationDTO;
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
    private final ReservationDAO reservationDAO;
    private final SeatsDAO seatsDAO;

    public ReservationEntity reservationTry(Integer eventId, Integer seatNumber, String userId) {
        boolean locked = redisService.reservationTry(
                eventId,
                seatNumber,
                userId
        );
        if(!locked) {
            throw new CustomException(ErrorCode.ALREADY_RESERVATION);
        }

        EventEntity event = eventService.getEvent(eventId);
        if(event == null) {
            redisService.deleteReservation(eventId, seatNumber);
            throw new CustomException(ErrorCode.NOT_FOUND_EVENT);
        }


        SeatsEntity seatEntity = seatsService.getSeat(seatNumber);
        if(seatEntity == null){
            redisService.deleteReservation(eventId, seatNumber);
            throw new CustomException(ErrorCode.NOT_FOUND_SEAT);
        }
        if(!seatEntity.getStatus().equals(SeatStatus.AVAILABLE)) {
            throw new CustomException(ErrorCode.ALREADY_RESERVATION);
        }

        ReservationEntity reservationEntity = ReservationEntity.builder()
                .eventId(eventId)
                .seatNumber(seatNumber)
                .userId(userId)
                .status(SeatStatus.WAITING)
                .build();

        seatEntity.setStatus(SeatStatus.WAITING);
        seatsService.save(seatEntity);

        return reservationRepository.save(reservationEntity);
    }


    public void reservationConfirm(ReservationDTO reservationDTO) {
        int result = reservationDAO.reservationConfirm(reservationDTO);
        if(result == 1){
            seatsDAO.seatConfirm(reservationDTO);
            redisService.deleteReservation(reservationDTO.getEventId(), reservationDTO.getSeatNumber());
        }
        else{
            throw new CustomException(ErrorCode.EXIST_USERID);
        }

    }
}
