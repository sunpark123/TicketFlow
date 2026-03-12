package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.AOP.AdminOnly;
import com.sunpark.ticketflow.DTO.EventDTO;
import com.sunpark.ticketflow.Entity.EventEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.EventStatus;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Repository.EventRepository;
import com.sunpark.ticketflow.Service.EventService;
import com.sunpark.ticketflow.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @AdminOnly
    @PostMapping("")
    public ResponseEntity<String> createEvent(@Valid @RequestBody EventDTO eventDTO, @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole) {
        eventService.makeEvent(eventDTO);
        return new ResponseEntity<>("Success Made Event", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getEvents() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }
}
