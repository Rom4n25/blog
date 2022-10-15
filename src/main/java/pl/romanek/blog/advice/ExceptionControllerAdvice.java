package pl.romanek.blog.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.romanek.blog.exception.UnauthorizedOperationException;
import pl.romanek.blog.exception.UsernameExistsException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<Void> unauthorizedOperationException() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Void> usernameExistsException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
