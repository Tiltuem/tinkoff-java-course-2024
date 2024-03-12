package edu.java.exception;

public class UserIsNotRegisteredException extends RuntimeException {
    public UserIsNotRegisteredException(String message) {
        super(message);
    }
}
