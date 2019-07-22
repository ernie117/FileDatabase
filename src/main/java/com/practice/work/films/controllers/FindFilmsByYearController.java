package com.practice.work.films.controllers;

import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.Film;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch Films by Exact Date"})
public class FindFilmsByYearController {

    private FilmsService filmsService;

    @Autowired
    FindFilmsByYearController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping(path = "/v1/findAllByYear")
    public List<Film> fetchAllFilmsByReleaseDate(@ApiParam("Year to search, as string")
                                                 @RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
    return this.filmsService.findFilmsByReleaseDate(localDate);
    }

}
