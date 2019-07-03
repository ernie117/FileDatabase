package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"Find Films by Year"})
public class FindFilmsByYearController {

    private FilmRepository filmRepository;

    FindFilmsByYearController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping(path = "/v1/findAllByYear")
    public List<Film> fetchAllFilmsByYear(@ApiParam(value = "Year to search, as int")
                                          @RequestParam int year) {
        return this.filmRepository.findFilmsByYearReleased(year);
    }
}
