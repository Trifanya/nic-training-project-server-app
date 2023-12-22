package dev.trifanya.taskmanagementsystem.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import javax.validation.ConstraintViolationException;
import dev.trifanya.taskmanagementsystem.exception.NotFoundException;
import dev.trifanya.taskmanagementsystem.exception.InvalidDataException;
import dev.trifanya.taskmanagementsystem.exception.AlreadyExistException;
import dev.trifanya.taskmanagementsystem.exception.UnavailableActionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<CustomErrorResponse> handleException(AlreadyExistException exception) {
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleException(NotFoundException exception) {
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    // ConstraintViolationException - исключение, выбрасываемое при нарушении ограничений в БД
    @ExceptionHandler({InvalidDataException.class, ConstraintViolationException.class})
    public ResponseEntity<CustomErrorResponse> handleException(InvalidDataException exception) {
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableActionException.class)
    public ResponseEntity<CustomErrorResponse> handleException(UnavailableActionException exception) {
        return new ResponseEntity<>(new CustomErrorResponse(exception.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomErrorResponse> handleException(BadCredentialsException exception) {
        return new ResponseEntity<>(new CustomErrorResponse("Вы ввели неверный пароль."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(new CustomErrorResponse("Некорректное тело запроса."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Исключение, выбрасываемоое при нарушении ограничений у объекта с аннотацией @Valid
    public ResponseEntity<CustomErrorResponse> handleException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getFieldErrors();
        StringBuilder message = new StringBuilder();
        for (FieldError error : errors) {
            message.append(error.getDefaultMessage() + "\n");
        }
        return new ResponseEntity<>(new CustomErrorResponse(message.toString()), HttpStatus.BAD_REQUEST);
    }
}
