package ru.javawebinar.topjava.util.exception;

public class NotValidEntityException extends RuntimeException {
    public NotValidEntityException(String message) {
        super(message);
    }
}