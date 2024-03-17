package edu.java.exception;

public class SiteAlreadyExistsException extends RuntimeException {
    public SiteAlreadyExistsException(String message) {
        super(message);
    }
}
