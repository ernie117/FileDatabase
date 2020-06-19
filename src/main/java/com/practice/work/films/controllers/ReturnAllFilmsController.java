package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch All Films"})
public class ReturnAllFilmsController {

    private final FilmsService filmsService;
    private final ConfigProperties configProperties;
    private final ModelMapper modelMapper;

    @Autowired
    ReturnAllFilmsController(FilmsService filmsService, ConfigProperties configProperties, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/v1/all")
    public ResponseEntity<List<FilmDTO>> fetchAllFilmDocuments() {
        log.info("Fetch All Films endpoint called.");
        return filmsService.fetchAllFilms()
                .map(films -> {
                    log.info("Fetch All Films endpoint retrieved {} film object/s from DB.", films.size());
                    return ResponseEntity
                            .ok()
                            .location(URI.create(configProperties.getFindAllFilmsURI()))
                            .body(films
                                    .stream()
                                    .map(film -> modelMapper.map(film, FilmDTO.class))
                                    .collect(Collectors.toList()));
                }).orElse(ResponseEntity.notFound().build());
    }
}
