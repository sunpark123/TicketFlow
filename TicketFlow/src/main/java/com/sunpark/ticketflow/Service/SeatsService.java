package com.sunpark.ticketflow.Service;

import com.sunpark.ticketflow.Entity.SeatsEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.SeatStatus;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Mapper.SeatsMapper;
import com.sunpark.ticketflow.Repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatsService {
    private final SeatsRepository seatsRepository;
    private final SeatsMapper seatsMapper;


    public List<SeatsEntity> getSeatsByEventId(Integer eventId) {
        return getSeatsByEventId(eventId);
    }

    public void bulkCreateSeats(Integer eventId) {
        List<SeatsEntity> seatsEntityList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            seatsEntityList.add(
                SeatsEntity.builder()
                    .eventId(eventId)
                    .seatNumber(i)
                    .status(SeatStatus.AVAILABLE)
                    .build()
            );
        }
        seatsMapper.bulkInsert(seatsEntityList);
    }

    public SeatsEntity getSeatOrThrow(Integer seatNumber) {
        return seatsRepository.findBySeatNumber(seatNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SEAT));
    }

    public void save(SeatsEntity seatsEntity) {
        seatsRepository.save(seatsEntity);
    }
}
