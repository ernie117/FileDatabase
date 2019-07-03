package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"Find Film by Genre"})
public class FindFilmsByGenreController {

    private FilmRepository filmRepository;

    @Autowired
    FindFilmsByGenreController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping(path = "/v1/findAllByGenre")
    public List<Film> fetchAllFilmsByGenre(@ApiParam(value = "Genre to search as a string. Case-insensitive")
                                           @RequestParam String genre) {
        return this.filmRepository.findFilmsByGenreIgnoreCase(genre);
    }

}
