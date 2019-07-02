package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseStatus(HttpStatus.CREATED)
public class AddFilmController {

    private FilmRepository filmRepository;

    @Autowired
    AddFilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @RequestMapping(value = "/addFilm", method = RequestMethod.POST)
    public Film create(@RequestBody Film film) {
        return filmRepository.save(film);
    }
}
