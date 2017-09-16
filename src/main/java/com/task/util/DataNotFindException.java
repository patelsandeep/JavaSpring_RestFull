package com.task.util;

public class DataNotFindException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public DataNotFindException(String message) {
        super(message);
    }

}
