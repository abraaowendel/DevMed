package com.api.devmed.services.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ResourceNotFoundException  extends EntityNotFoundException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
