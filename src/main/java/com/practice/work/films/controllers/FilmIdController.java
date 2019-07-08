package com.practice.work.films.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.practice.work.films.Service.FilmsService;
import com.practice.work.films.entities.Film;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"Find Film IDs by Title"})
public class FilmIdController {

    private FilmsService filmsService;

    @Autowired
    FilmIdController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping(value = "/v1/getFilmId")
    public JsonNode getFilmIdsByTitle(@ApiParam("Film title to search, as string")
                                      @RequestParam String title) {
        return this.filmsService.getFilmIdsByTitle(title);
    }
}
