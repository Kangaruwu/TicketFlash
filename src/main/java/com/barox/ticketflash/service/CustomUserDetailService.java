package com.barox.ticketflash.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.dto.request.RegisterRequest;

public interface CustomUserDetailService extends UserDetailsService {

    public CustomUserDetails loadUserByUsernameOrEmail(String usernameOrEmail);
    public CustomUserDetails register(RegisterRequest request);

}
