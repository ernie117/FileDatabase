package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus(HttpStatus.CREATED)
public class AddMultipleFilms {

    private FilmRepository filmRepository;

    @Autowired
    AddMultipleFilms(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @RequestMapping(value = "/addMultipleFilms", method = RequestMethod.POST)
    public List<Film> addMultipleFilms(@RequestBody List<Film> films) {
        return this.filmRepository.saveAll(films);
    }

}
