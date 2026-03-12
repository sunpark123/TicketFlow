package com.sunpark.ticketflow.Service;

import com.sunpark.ticketflow.Entity.SeatsEntity;
import com.sunpark.ticketflow.Repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsService {
    private final SeatsRepository seatsRepository;

    public List<SeatsEntity> getSeatsByEventId(Integer eventId) {
        return getSeatsByEventId(eventId);
    }
}
