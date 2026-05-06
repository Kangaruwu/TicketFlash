package com.barox.ticketflash.controller;

import com.barox.ticketflash.annotation.RateLimit;
import com.barox.ticketflash.dto.request.CheckInRequest;
import com.barox.ticketflash.dto.response.CheckInResponse;
import com.barox.ticketflash.service.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @RateLimit(capacity = 20, refillTokens = 20)
    public ResponseEntity<CheckInResponse> checkIn(@Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.ok(checkInService.checkIn(request));
    }
}
