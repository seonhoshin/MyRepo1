package com.posting.advice;

import com.posting.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
//        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//
//        List<ErrorResponse.FieldError> errors =
//                fieldErrors.stream()
//                        .map(error -> new ErrorResponse.FieldError(
//                                error.getField(),
//                                error.getRejectedValue(),
//                                error.getDefaultMessage()
//                        ))
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e
    ){

     //   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }
}
