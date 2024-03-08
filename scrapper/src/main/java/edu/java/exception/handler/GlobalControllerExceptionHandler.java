package edu.java.exception.handler;

import edu.java.dto.ApiErrorResponse;
import edu.java.exception.LinkAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackedException;
import edu.java.exception.UserAlreadyRegisteredException;
import edu.java.exception.UserIsNotRegisteredException;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(value = {
        LinkAlreadyTrackedException.class,
        UserAlreadyRegisteredException.class,
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

    @ExceptionHandler(LinkIsNotTrackedException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(Exception ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.toString(),
            ex.getClass().getName(),
            ex.getMessage(),
            Collections.singletonList(ex.getStackTrace().toString())
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UserIsNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedException(Exception ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            HttpStatus.UNAUTHORIZED.toString(),
            ex.getClass().getName(),
            ex.getMessage(),
            Collections.singletonList(ex.getStackTrace().toString())
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
