package edu.java.bot.exception;

public class LinkIsNotTrackedException extends RuntimeException {
    public LinkIsNotTrackedException(String message) {
        super(message);
    }

    /*public LinkIsNotTrackedException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<LinkIsNotTrackedException> linkIsNotTrackedException(String message, Object... args) {
        return () -> new LinkIsNotTrackedException(message, args);
    }

    public static Supplier<LinkIsNotTrackedException> linkIsNotTrackedException(String message) {
        return () -> new LinkIsNotTrackedException(message);
    }*/
}
