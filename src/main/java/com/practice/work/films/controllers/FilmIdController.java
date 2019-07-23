package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

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
    public ResponseEntity<?> getFilmIdsByTitle(@ApiParam("Film title to search, as string")
                                      @RequestParam String title) {
        return this.filmsService.getFilmIdsByTitle(title)
                .map(films -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                .location(new URI(configProperties.getGetFilmIdURI()))
                                .body(films);
                    } catch (URISyntaxException use) {
                        return ResponseEntity.badRequest().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }
}
