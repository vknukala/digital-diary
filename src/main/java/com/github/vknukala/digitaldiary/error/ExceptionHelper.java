package com.github.vknukala.digitaldiary.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static java.lang.String.format;


@ControllerAdvice
@Slf4j
public class ExceptionHelper {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex,
                                                                     HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse("Not found",
                format("No user found for id - %s",ex.getUserId()));
        log.error("'{}' while accessing  {} \n", errorResponse.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleInputValidationException(BindException ex, HttpServletRequest httpServletRequest) {
        List<String> error = new ArrayList<>();
        ex.getFieldErrors().forEach(err->error.add(new StringJoiner(":")
                .add(err.getField()).add(err.getDefaultMessage()).toString()));
        ErrorResponse errorResponse = new ErrorResponse("Invalid input",
                error.toString());
        log.error("'{}' while accessing {}\n", errorResponse.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse("Server error",
                format("Unable fulfill the request - %s",ex.getMessage()));
        log.error("'{}' while accessing  {} \n",ex.getMessage(),httpServletRequest.getRequestURI());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
