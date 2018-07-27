package com.sadi.toor.recommend.core.wrapper;

public class ErrorObject {

    private final Status status;
    private final String message;

    public ErrorObject(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorObject(String message) {
        this.message = message;
        this.status = Status.ERROR;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
