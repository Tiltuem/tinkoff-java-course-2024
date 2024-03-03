package edu.java.bot.exception;

public class UserIsNotRegisteredException extends RuntimeException {
    public UserIsNotRegisteredException(String message) {
        super(message);
    }

   /* public UserIsNotRegisteredException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<UserIsNotRegisteredException> userIsNotRegisteredException(String message, Object... args) {
        return () -> new UserIsNotRegisteredException(message, args);
    }

    public static Supplier<UserIsNotRegisteredException> userIsNotRegisteredException(String message) {
        return () -> new UserIsNotRegisteredException(message);
    }*/
}
