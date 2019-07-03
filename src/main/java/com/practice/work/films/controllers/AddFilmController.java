package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@ResponseStatus(HttpStatus.CREATED)
@Api(tags = {"Add Film"})
public class AddFilmController {

    private FilmRepository filmRepository;

    @Autowired
    AddFilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @PostMapping(value = "/v1/addFilm")
    public Film insertFilmDocument(@Valid @RequestBody Film film) {
        return filmRepository.save(film);
    }
}
