package com.barox.ticketflash.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;
import com.barox.ticketflash.dto.response.ErrorResponse;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;


@RestControllerAdvice
@RequiredArgsConstructor // de inject environment
@Slf4j
public class GlobalExceptionHandler {

    private final Environment environment;

    // dedicated exception handlers ------------------------
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
        return new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Not Found", 
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
    }

    // generic exception handlers ------------------------
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException ex, WebRequest request) {
        return new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request", 
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
    }

    // Khi @Valid bắt đc lỗi
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    // Internal Server Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(Exception ex, WebRequest request) {
        log.error("Uncaught exception: ", ex);

        boolean isDev = Arrays.stream(environment.getActiveProfiles())
                .anyMatch(profile -> profile.equalsIgnoreCase("dev") || profile.equalsIgnoreCase("local"));

        String message;
        if (isDev) {
            message = ex.getMessage(); 
        } else {
            message = "Something went wrong. Please contact support.";
        }

        return new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error", 
            message, 
            request.getDescription(false).replace("uri=", "")
        );
    }
        
}
