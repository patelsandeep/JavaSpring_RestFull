package com.task.util;

public class MissingRequiredFieldException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public MissingRequiredFieldException(String message) {
        super(message);
    }

}
