package com.sunpark.ticketflow.Service;


import com.sunpark.ticketflow.DTO.EventDTO;
import com.sunpark.ticketflow.Entity.EventEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.EventStatus;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;


    public List<EventEntity> getEvents() {
        return eventRepository.findAllOrderByStatus();
    }

    public EventEntity getEventById(int eventId) {
        Optional<EventEntity> eventEntity = eventRepository.findById(eventId);
        if(eventEntity.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_EVENT);
        }

        return eventEntity.get();
    }

    public void makeEvent(EventDTO eventDTO) {

        EventEntity eventEntity = EventEntity.builder()
                .title(eventDTO.getTitle())
                .description(eventDTO.getDescription())
                .category(eventDTO.getCategory())
                .status(EventStatus.ENROLLMENT)
                .booking_start_at(eventDTO.getBooking_start_at())
                .booking_end_at(eventDTO.getBooking_end_at())
                .build();

        eventRepository.save(eventEntity);
    }
}
