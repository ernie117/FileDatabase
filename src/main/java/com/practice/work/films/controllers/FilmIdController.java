package com.practice.work.films.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Api(tags = {"Fetch Film IDs by Title"})
public class FilmIdController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;

    @Autowired
    FilmIdController(FilmsService filmsService, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
    }

    @GetMapping(value = "/v1/getFilmId")
    public ResponseEntity<JsonNode> getFilmIdsByTitle(@ApiParam("Film title to search, as string")
                                               @RequestParam String title) {
        return this.filmsService.getFilmIdsByTitle(title)
                .map(jsonNode -> ResponseEntity
                        .ok()
                        .location(URI.create(configProperties.getGetFilmIdURI()))
                        .body(jsonNode))
                .orElse(ResponseEntity.notFound().build());
    }
}
