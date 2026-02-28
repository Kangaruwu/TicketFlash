package com.barox.ticketflash.controller;

import org.springframework.web.bind.annotation.RestController;
import com.barox.ticketflash.dto.response.PaymentResponse;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.service.PaymentService;
import lombok.AllArgsConstructor;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{bookingId}/pay")
    public ResponseEntity<PaymentResponse> payForBooking(
        @PathVariable UUID bookingId, 
        @AuthenticationPrincipal CustomUserDetails userDetails) 
    {    
        return ResponseEntity.ok(
            paymentService.processPayment(bookingId, userDetails)
        );
    }
    
}
