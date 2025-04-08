package pl.pwr.trash.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.pwr.trash.response.ResponseRCF7808;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseRCF7808 handleAccessDeniedException(RuntimeException e){
        return new ResponseRCF7808(
                "api/v1/user/errors/acces-denied",
                "You try use not your data",
                "Add your id to the path",
                401,
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseRCF7808 handleResourceNotFound(RuntimeException e){
        return new ResponseRCF7808(
                "api/v1/resoures/not-found",
                "Your resource doesn't exist",
                "Check if your data are valid",
                400,
                e.getMessage()
        );
    }


}
