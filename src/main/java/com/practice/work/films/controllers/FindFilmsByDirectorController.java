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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.work.films.constants.FilmsConstants.NAME_REGEX;


@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Fetch Films by Director"})
@Validated
public class FindFilmsByDirectorController {

    private final FilmsService filmsService;
    private final ConfigProperties configProperties;
    private final ModelMapper modelMapper;

    @Autowired
    FindFilmsByDirectorController(FilmsService filmsService, ConfigProperties configProperties, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/v1/findFilmsByDirector")
    public ResponseEntity<List<FilmDTO>> fetchFilmsByDirector(@ApiParam("Director to search, as a string. Case-insensitive")
                                                              @Pattern(regexp = NAME_REGEX)
                                                              @RequestParam String director) {
        return this.filmsService.findFilmsByDirector(director)
                .map(films -> ResponseEntity
                        .ok()
                        .location(URI.create(configProperties.getFindFilmsByDirectorURI()))
                        .body(films
                                .stream()
                                .map(film -> modelMapper.map(film, FilmDTO.class))
                                .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }
}
