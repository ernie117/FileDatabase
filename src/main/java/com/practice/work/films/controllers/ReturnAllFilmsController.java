package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"Fetch All Films"})
public class ReturnAllFilmsController {

    private FilmRepository filmRepository;

    @Autowired
    ReturnAllFilmsController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping(value = "/v1/all", produces = "application/json")
    public List<Film> fetchAllFilmDocuments() {
        return filmRepository.findAll();
    }
}
