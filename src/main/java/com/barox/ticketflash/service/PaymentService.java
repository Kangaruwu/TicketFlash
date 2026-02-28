package com.barox.ticketflash.service;

import java.util.UUID;
import com.barox.ticketflash.dto.response.PaymentResponse;
import com.barox.ticketflash.security.CustomUserDetails;

public interface PaymentService {
    public PaymentResponse processPayment(UUID bookingId, CustomUserDetails userDetails);
}
