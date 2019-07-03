package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/v1/findFilmByTitle", produces = "application/json")
@Api(tags = {"Find Film by Title"})
public class FindFilmByTitleController {

    private FilmRepository filmRepository;

    @Autowired
    FindFilmByTitleController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping(produces = "application/json")
    public List<Film> fetchFilmByTitle(@ApiParam("Film title to search, as string. Case-insensitive")
                                       @RequestParam String title) {
        return filmRepository.findFilmByTitleRegexIgnoreCase(title);
    }
}
