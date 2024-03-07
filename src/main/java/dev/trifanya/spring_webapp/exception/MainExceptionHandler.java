package dev.trifanya.spring_webapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.jms.JMSException;

@ControllerAdvice
public class MainExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MainExceptionHandler.class);

    @ExceptionHandler(JMSException.class)
    public void handleException(JMSException exception) {
        logger.error("Произошла ошибка при отправке или получении сообщения.", exception);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public void handleException(JsonProcessingException exception) {
        logger.error("Произошла ошибка при формировании или чтении сообщения.", exception);
    }
}
