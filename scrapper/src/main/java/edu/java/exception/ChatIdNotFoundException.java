package edu.java.exception;

public class ChatIdNotFoundException extends RuntimeException {
    public ChatIdNotFoundException(String message) {
        super(message);
    }
}
