package com.barox.ticketflash.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.barox.ticketflash.service.EventService;
import com.barox.ticketflash.annotation.RateLimit;
import com.barox.ticketflash.dto.request.EventRequest;
import com.barox.ticketflash.dto.response.EventResponse;
import com.barox.ticketflash.dto.response.PagedResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request));
    }

    @GetMapping
    @RateLimit(capacity = 5, refillTokens = 5) // Giới hạn 5 req/phút cho endpoint này
    public ResponseEntity<PagedResponse<EventResponse>> getAllEvents(
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "10", required = false) int size,
        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return ResponseEntity.ok(eventService.getAllEvents(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
    

}
