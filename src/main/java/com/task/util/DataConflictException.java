package com.task.util;

public class DataConflictException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public DataConflictException(String message) {
        super(message);
    }

}
