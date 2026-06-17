package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        return Map.of("erro", ex.getMessage());
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationException(org.springframework.security.core.AuthenticationException ex) {
        return Map.of("erro", "Credenciais inválidas: " + ex.getMessage());
    }

}
