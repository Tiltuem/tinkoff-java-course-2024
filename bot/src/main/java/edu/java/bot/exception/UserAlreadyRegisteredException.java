package edu.java.bot.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

   /* public UserAlreadyRegisteredException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<UserAlreadyRegisteredException> userAlreadyRegisteredException(
        String message,
        Object... args
    ) {
        return () -> new UserAlreadyRegisteredException(message, args);
    }

    public static Supplier<UserAlreadyRegisteredException> userAlreadyRegisteredException(String message) {
        return () -> new UserAlreadyRegisteredException(message);
    }*/
}
