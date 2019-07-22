package com.practice.work.films.controllers;

import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch Films by Title"})
public class FindFilmByTitleController {

    private FilmsService filmsService;

    @Autowired
    FindFilmByTitleController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping(value = "/v1/findFilmByTitle", produces = "application/json")
    public ResponseEntity<?> fetchFilmByTitle(@ApiParam("Film title to search, as string. Case-insensitive")
                                       @RequestParam String title) {

        return this.filmsService.findFilmsByTitleRegexIgnoreCase(title)
                .map(films -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(String.valueOf(films.get(0).getDateAdded()))
                                .location(new URI("/v1/findFilmByTitle"))
                                .body(films);
                    } catch (URISyntaxException use) {
                        return ResponseEntity.badRequest().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }
}
