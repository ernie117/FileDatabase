package com.practice.work.films.controllers;

import com.practice.work.films.Service.FilmsService;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch All Films"})
public class ReturnAllFilmsController {

    private FilmsService filmsService;

    @Autowired
    ReturnAllFilmsController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping(value = "/v1/all", produces = "application/json")
    public List<Film> fetchAllFilmDocuments() {
        return filmsService.fetchAllFilms();
    }
}
