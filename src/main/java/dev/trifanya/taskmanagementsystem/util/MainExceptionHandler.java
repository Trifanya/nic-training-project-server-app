package dev.trifanya.taskmanagementsystem.util;

import dev.trifanya.taskmanagementsystem.exception.AlreadyExistException;
import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<CustomErrorResponse> handleException(AlreadyExistException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleException(NotFoundException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
