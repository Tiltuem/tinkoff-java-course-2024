package edu.java.bot.exception.handler;

import edu.java.bot.exception.LinkAlreadyTrackedException;
import edu.java.bot.exception.LinkIsNotTrackedException;
import edu.java.bot.exception.UserAlreadyRegisteredException;
import edu.java.bot.exception.UserIsNotRegisteredException;
import edu.java.dto.ApiErrorResponse;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(value = {
        LinkAlreadyTrackedException.class,
        LinkIsNotTrackedException.class,
        UserAlreadyRegisteredException.class,
        UserIsNotRegisteredException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflictException(Exception ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            HttpStatus.CONFLICT.toString(),
            ex.getClass().getName(),
            ex.getMessage(),
            Collections.singletonList(ex.getStackTrace().toString())
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
