package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = "Fetch Films by Cinematographer")
@Validated
public class FindFilmsByCinematographerController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;
    private ModelMapper modelMapper;

    @Autowired
    FindFilmsByCinematographerController(FilmsService filmsService, ConfigProperties configProperties, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/v1/findFilmsByCinematographer")
    public ResponseEntity<?> fetchFilmsByCinematographer(@Valid
                                                         @ApiParam("String of actor to search; case-insensitive")
                                                         @Pattern(regexp = "[a-zA-Z]+")
                                                         @RequestParam String cinematographer) {
        return this.filmsService.fetchFilmsByCinematographer(cinematographer)
                .map(films -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                .location(new URI(configProperties.getFindFilmsByCinematographerURI()))
                                .body(films
                                        .stream()
                                        .map(film -> modelMapper.map(film, FilmDTO.class))
                                        .collect(Collectors.toList()));
                    } catch (URISyntaxException use) {
                        return ResponseEntity.badRequest().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

}
