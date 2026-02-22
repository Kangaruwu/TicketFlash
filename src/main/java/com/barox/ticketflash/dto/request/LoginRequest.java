package com.barox.ticketflash.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotNull(message = "Username or email is required")
    private String usernameOrEmail;

    @NotNull(message = "Password is required")
    private String password;

}
