package com.example.todo.global.exception;

public abstract class ApplicationException extends RuntimeException {
    private final int status;

    public ApplicationException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

