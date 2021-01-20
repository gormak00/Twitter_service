package by.application.Twitter.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidCredentials.class)
    protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new AwesomeException("Invalid credentials"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotUnic.class)
    protected ResponseEntity<AwesomeException> handleNotUnic() {
        return new ResponseEntity<>(new AwesomeException("Username is not unic"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPathVariable.class)
    protected ResponseEntity<AwesomeException> handleInvalidPathVariable() {
        return new ResponseEntity<>(new AwesomeException("Invalid path variable"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotConfirmation.class)
    protected ResponseEntity<AwesomeException> handleNotConfirmation() {
        return new ResponseEntity<>(new AwesomeException("Awaiting confirmation by mail"), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}
