package com.practice.work.films.Validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ControllerAdvice
public class ValidationErrorResponse {

    private final Set<Violation> VIOLATIONS = new HashSet<>();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleValidationExceptions(MethodArgumentNotValidException ex) {

        if (Optional.ofNullable(ex.getBindingResult().getFieldErrors()).isPresent()) {
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                VIOLATIONS.add(
                        Violation.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build()
                );
            }
        }

        return VIOLATIONS;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        VIOLATIONS.add(
                Violation.builder()
                        .field(ex.getName())
                        .message("Date must be in format yyyy-mm-dd")
                        .build()
        );

        return VIOLATIONS;
    }
}
