package edu.java.bot.exception;

public class LinkAlreadyTrackedException extends RuntimeException {
    public LinkAlreadyTrackedException(String message) {
        super(message);
    }

    /*public LinkAlreadyTrackedException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<ChatIdNotFoundException> chatIdNotFoundException(String message, Object... args) {
        return () -> new ChatIdNotFoundException(message, args);
    }

    public static Supplier<ChatIdNotFoundException> chatIdNotFoundException(String message) {
        return () -> new ChatIdNotFoundException(message);
    }*/
}
