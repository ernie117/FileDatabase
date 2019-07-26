package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@ResponseStatus(HttpStatus.CREATED)
@Api(tags = {"Add Multiple Films"})
public class AddMultipleFilms {

    private FilmsService filmsService;

    @Autowired
    AddMultipleFilms(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @PostMapping(value = "/v1/addMultipleFilms")
    public List<Film> insertManyFilmDocuments(@Valid
                                              @ApiParam("Array of film objects, structured as in the example")
                                              @RequestBody List<Film> films) {
        return this.filmsService.insertMultipleFilmDocument(films);
    }

}
