package pl.romanek.blog.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.romanek.blog.exception.PointAlreadyAddedException;
import pl.romanek.blog.exception.UnauthorizedOperationException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<Void> unauthorizedOperationException() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PointAlreadyAddedException.class)
    public ResponseEntity<Void> pointAlreadyAddedException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
