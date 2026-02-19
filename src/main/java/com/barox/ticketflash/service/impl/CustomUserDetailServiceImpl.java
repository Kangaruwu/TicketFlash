package com.barox.ticketflash.service.impl;

import org.springframework.stereotype.Service;
import com.barox.ticketflash.dto.request.RegisterRequest;
import com.barox.ticketflash.entity.User;
import com.barox.ticketflash.enums.UserRole;
import com.barox.ticketflash.exception.DataNotFoundException;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import com.barox.ticketflash.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public CustomUserDetails loadUserByUsername(String usernameOrEmail) {
        return loadUserByUsernameOrEmail(usernameOrEmail);
    }

    @Override
    public CustomUserDetails loadUserByUsernameOrEmail(String usernameOrEmail) throws DataNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new DataNotFoundException("User not found with username or email: " + usernameOrEmail));
        return CustomUserDetails.mapToUserDetails(user);
    }

    @Override
    public CustomUserDetails register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(UserRole.USER);
        user.setFullName(request.getFullName());
        return CustomUserDetails.mapToUserDetails(userRepository.save(user));
    }

}