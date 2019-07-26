package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch Films by Genre"})
public class FindFilmsByGenreController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;

    @Autowired
    FindFilmsByGenreController(FilmsService filmsService, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
    }

    @GetMapping(path = "/v1/findAllByGenre")
    public ResponseEntity<?> fetchAllFilmsByGenre(@ApiParam("Genre to search as a string. Case-insensitive")
                                           @RequestParam String genre) {
        return this.filmsService.findFilmsByGenre(genre)
                .map(films -> {
                    try {
                        return ResponseEntity.ok()
                                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                .location(new URI(configProperties.getFindFilmsByGenreURI()))
                                .body(films);
                    } catch (URISyntaxException use) {
                        return ResponseEntity.badRequest().build();
                    }
                }).orElse(ResponseEntity.badRequest().build());
    }

}
