package com.practice.work.films.controllers;

import com.practice.work.films.Service.FilmsService;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Optional;


@RestController
@Api(tags = {"Add Film"})
public class AddFilmController {

    private FilmsService filmsService;

    @Autowired
    AddFilmController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @PostMapping(value = "/v1/addFilm")
    @ResponseStatus(HttpStatus.CREATED)
    public Film insertFilmDocument(@Valid
                                   @ApiParam("Film json object, structured as in the example")
                                   @RequestBody Film film) {
        return filmsService.insertSingleFilmDocument(film);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        String field = null, error = null;
        Optional<FieldError> optionalString = Optional.ofNullable(ex.getBindingResult().getFieldError());

        if (optionalString.isPresent()) {
            field = ex.getBindingResult().getFieldError().getField();
            error = ex.getBindingResult().getFieldError().getDefaultMessage();
        }

        return String.format("%s: %s", field, error);
    }

}
