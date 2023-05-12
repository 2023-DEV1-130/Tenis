package com.kata.tennis.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorMessage> handleValidationException(Exception ex) {
        String message = "Exception occurred. Please contact with support team.";
        ErrorMessage errorMessage = new ErrorMessage(BAD_REQUEST, message, ex);
        return buildResponseEntity(errorMessage);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(MethodArgumentNotValidException ex) {
        String message = "Bad request";
        ErrorMessage errorMessage = new ErrorMessage(BAD_REQUEST, message, ex);
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessage.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildResponseEntity(errorMessage);
    }

    private ResponseEntity<ErrorMessage> buildResponseEntity(ErrorMessage errorMessage) {
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
}
