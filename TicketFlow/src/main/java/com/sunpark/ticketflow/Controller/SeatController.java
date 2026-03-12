package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.Service.EventService;
import com.sunpark.ticketflow.Service.SeatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class SeatController {
    private final SeatsService seatsService;
    private final EventService eventService;

    @GetMapping("/{eventId}/seats")
    public ResponseEntity<?> getSeats(@PathVariable Integer eventId) {
        return ResponseEntity.ok(seatsService.getSeatsByEventId(eventId));
    }
}
