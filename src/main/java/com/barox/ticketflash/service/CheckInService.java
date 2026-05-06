package com.barox.ticketflash.service;

import com.barox.ticketflash.dto.request.CheckInRequest;
import com.barox.ticketflash.dto.response.CheckInResponse;

public interface CheckInService {
    CheckInResponse checkIn(CheckInRequest request);
}
