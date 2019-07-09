package com.practice.work.films.controllers;

import com.practice.work.films.Service.FilmsService;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch Films by Director"})
public class FindFilmsByDirectorController {

    private FilmsService filmsService;

    @Autowired
    FindFilmsByDirectorController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping(path = "/v1/fetchFilmsByDirector")
    public List<Film> fetchFilmsByDirector(@ApiParam("Director to search, as a string. Case-insensitive")
                                           @RequestParam String director) {
        return this.filmsService.findFilmsByDirector(director);
    }
}