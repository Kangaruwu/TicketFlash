package com.barox.ticketflash.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import com.barox.ticketflash.dto.response.LoginResponse;
import com.barox.ticketflash.dto.response.RegisterResponse;
import com.barox.ticketflash.dto.response.TokenResponse;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.security.JwtTokenProvider;
import com.barox.ticketflash.service.CustomUserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.barox.ticketflash.dto.request.LoginRequest;
import com.barox.ticketflash.dto.request.RegisterRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (
            request.getUsernameOrEmail(), 
            request.getPassword()
        );
       
        Authentication authentication = authenticationManager.authenticate(authToken);

        TokenResponse tokens = jwtTokenProvider.generateToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse response = new LoginResponse();
        response.setTokens(tokens);
        response.setUser(
            CustomUserDetails.mapToUserResponse(
                customUserDetailService.loadUserByUsernameOrEmail(request.getUsernameOrEmail())
            )
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        CustomUserDetails userDetails = customUserDetailService.register(request);
        RegisterResponse response = new RegisterResponse();
        response.setUser(CustomUserDetails.mapToUserResponse(userDetails));
        return ResponseEntity.ok(response);
    }
    
}
