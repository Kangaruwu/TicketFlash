package com.barox.ticketflash.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;
import com.barox.ticketflash.dto.response.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;
import java.util.HashMap;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // dedicated exception handler
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
        return new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error", 
            "Something went wrong",
            request.getDescription(false).replace("uri=", "")
        );
    }
        
}
