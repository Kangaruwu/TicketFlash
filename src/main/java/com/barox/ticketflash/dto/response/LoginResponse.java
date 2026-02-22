package com.barox.ticketflash.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private TokenResponse tokens;
    private UserResponse user;
}
