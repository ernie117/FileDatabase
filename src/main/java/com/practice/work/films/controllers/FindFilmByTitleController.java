package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = {"Find Film by Title"})
public class FindFilmByTitleController {

    private FilmRepository filmRepository;

    @Autowired
    FindFilmByTitleController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping(value = "/v1/findFilmByTitle", produces = "application/json")
    public Film fetchFilmByTitle(@RequestParam String title) {
        return filmRepository.findFilmByTitleIgnoreCase(title);
    }
}
