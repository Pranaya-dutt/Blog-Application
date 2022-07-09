package com.mountblue.blog.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(PostNotFoundException postNotFoundException){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), postNotFoundException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(customErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(Exception exception){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(customErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(AccessDeniedException accessDeniedException){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(), accessDeniedException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(customErrorResponse,HttpStatus.UNAUTHORIZED);
    }
}
