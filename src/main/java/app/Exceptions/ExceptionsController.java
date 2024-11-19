package app.Exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<CustomException> handleException(MethodArgumentNotValidException e) {
        final List<CustomException> customExceptions = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new CustomException(error.getField(), error.getDefaultMessage()))
                .toList();
        return customExceptions;
    }
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public List<CustomException> onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<CustomException> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new CustomException(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return violations;
    }
}