package com.api.devmed.controllers.exceptions;
import com.api.devmed.controllers.exceptions.ErrorResponse;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorListResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private List<ErrorResponse> errors = new ArrayList<>();

    public ErrorListResponse() {
    }

    public ErrorListResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }

}
