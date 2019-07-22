package com.practice.work.films.controllers;

import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.Film;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api(tags = {"Add Film"})
@Validated
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

}
