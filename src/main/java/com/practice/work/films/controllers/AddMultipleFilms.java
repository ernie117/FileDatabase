package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus(HttpStatus.CREATED)
@Api(tags = {"Add Multiple Films"})
public class AddMultipleFilms {

    private FilmRepository filmRepository;

    @Autowired
    AddMultipleFilms(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @RequestMapping(value = "/v1/addMultipleFilms", method = RequestMethod.POST)
    public List<Film> insertManyFilmDocuments(@RequestBody List<Film> films) {
        return this.filmRepository.saveAll(films);
    }

}
