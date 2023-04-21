package com.api.devmed.services.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DataBaseException extends DataIntegrityViolationException {
    public DataBaseException(String msg) {
        super(msg);
    }
}
