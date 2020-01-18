package com.practice.work.films.validation;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Handling an instance of MethodArgumentNotValidException or its children", ex);
        Set<Violation> violations = new HashSet<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            violations.add(
                    Violation.builder()
                            .field(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                            .build()
            );
        }

        return violations;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        log.error("Handling an instance of MethodArgumentTypeMismatchException or its children", ex);
        Set<Violation> violations = new HashSet<>();
        violations.add(
                Violation.builder()
                        .field(ex.getName())
                        .message(ex.getName() + " should be " + ex.getRequiredType())
                        .build()
        );

        return violations;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleEmptyParameter(MissingServletRequestParameterException ex) {
        log.error("Handling an instance of MissingServletRequestParameterException or its children", ex);
        Set<Violation> violations = new HashSet<>();
        violations.add(
                Violation.builder()
                        .field(ex.getParameterName())
                        .message(ex.getLocalizedMessage())
                        .build()
        );

        return violations;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleMessageNotReadableError(HttpMessageNotReadableException ex) {
        log.error("Handling an instance of HttpMessageNotReadableException or its children", ex);
        Set<Violation> violations = new HashSet<>();
        violations.add(
                Violation.builder()
                        .field(ex.getClass().getSimpleName())
                        .message(ex.getCause().getLocalizedMessage())
                        .build()
        );

        return violations;
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleJsonParseException(JsonParseException ex) {
        log.error("Handling an instance of JsonParseException or its children", ex);
        Set<Violation> violations = new HashSet<>();
        violations.add(
                Violation.builder()
                        .field(ex.getClass().getSimpleName())
                        .message(ex.getLocalizedMessage())
                        .build()
        );

        return violations;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleConstraintException(ConstraintViolationException ex) {
        log.error("Handling an instance of ConstraintViolationException or its children", ex);
        Set<Violation> violations = new HashSet<>();
        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            // Simply splitting the PropertyPath string throws another exception
            // so we have to stream the nodes and get the names this way
            List<String> paths = new ArrayList<>();
            cv.getPropertyPath().forEach(node -> paths.add(node.getName()));

            violations.add(
                    Violation.builder()
                            .field(!paths.isEmpty() ? paths.get(paths.size() - 1) : "Unknown field.")
                            .message(cv.getMessage())
                            .build()
            );
        }

        return violations;
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<Violation> handleDatetimeParseError(DateTimeParseException ex) {
        log.error("Handling an instance of DateTimeParseException or its children", ex);
        Set<Violation> violations = new HashSet<>();
        violations.add(
                Violation.builder()
                        .field(ex.getClass().getSimpleName())
                        .message(ex.getLocalizedMessage())
                        .build()

        );

        return violations;
    }

}
