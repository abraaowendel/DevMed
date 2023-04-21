package com.api.devmed.controllers.exceptions;

import com.api.devmed.services.exceptions.DataBaseException;
import com.api.devmed.services.exceptions.ResourceNotFoundException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerlHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorListResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorListResponse errors = new ErrorListResponse();

        errors.setStatus(status);
        errors.setMessage("Parametros inválidos.");
        errors.setTimestamp(timestamp);

        exception.getBindingResult()
                 .getFieldErrors()
                 .forEach(ex -> errors.getErrors()
                 .add(new ErrorResponse(status.value(),ex.getDefaultMessage(),timestamp)));

        return ResponseEntity.status(status).body(errors);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorResponse> handleDataBaseException(DataBaseException exception){
        ErrorResponse error = copiarDados(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse error = copiarDados(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        ErrorResponse error = copiarDados(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception){
        ErrorResponse error = copiarDados(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    private ErrorResponse copiarDados(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(status.value());
        error.setMessage(message);
        error.setTimestamp(LocalDateTime.now());
        return error;
    }

}
