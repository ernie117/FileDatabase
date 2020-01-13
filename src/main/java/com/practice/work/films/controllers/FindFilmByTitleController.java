package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch Films by Title"})
public class FindFilmByTitleController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;
    private ModelMapper modelMapper;

    @Autowired
    FindFilmByTitleController(FilmsService filmsService, ConfigProperties configProperties, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/v1/findFilmByTitle", produces = "application/json")
    public ResponseEntity<List<FilmDTO>> fetchFilmByTitle(@ApiParam("Film title to search, as string. Case-insensitive")
                                              @RequestParam String title) {
        return this.filmsService.findFilmsByTitleRegexIgnoreCase(title)
                .map(films -> ResponseEntity
                        .ok()
                        .location(URI.create(configProperties.getFindFilmByTitleURI()))
                        .body(films
                                .stream()
                                .map(film -> modelMapper.map(film, FilmDTO.class))
                                .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }
}
