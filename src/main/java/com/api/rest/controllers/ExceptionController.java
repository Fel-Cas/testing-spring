package com.api.rest.controllers;

import com.api.rest.dtos.ResponseException;
import com.api.rest.exceptions.BadRequest;
import com.api.rest.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ResponseException> notFoundException(Exception exception, WebRequest request){
        ResponseException exceptionResponse= new ResponseException(LocalDateTime.now(),exception.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequest.class)
    public final ResponseEntity<ResponseException> badRequest(Exception exception, WebRequest request){
        ResponseException exceptionResponse= new ResponseException(LocalDateTime.now(),exception.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
