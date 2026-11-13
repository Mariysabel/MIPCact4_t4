package com.acti4.crudrapido.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Correo duplicado: 409 Conflict
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        return createResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request);
    }

    // Credenciales incorrectas: 401 Unauthorized
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        return createResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request);
    }

    // Recurso inexistente: 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return createResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request);
    }

    // JSON incorrecto: 400 Bad Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return createResponse(
                HttpStatus.BAD_REQUEST,
                "El cuerpo de la solicitud contiene un JSON inválido",
                request);
    }

    // Argumentos incorrectos: 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return createResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request);
    }

    // Método reutilizable para construir respuestas
    private ResponseEntity<Map<String, Object>> createResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        Map<String, Object> body = Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "path", request.getRequestURI());

        return ResponseEntity.status(status).body(body);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
            
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Los datos enviados no son válidos");
            
        return createResponse(HttpStatus.BAD_REQUEST, message, request);
    }
}