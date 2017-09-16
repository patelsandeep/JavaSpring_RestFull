package com.task.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private String errorMessage;
    private long errorCode;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, long errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
