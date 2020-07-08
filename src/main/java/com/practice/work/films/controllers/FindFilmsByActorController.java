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

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.work.films.constants.FilmsConstants.NAME_REGEX;

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = "Fetch Films by Actor")
@Validated
public class FindFilmsByActorController {

    private final FilmsService filmsService;
    private final ConfigProperties configProperties;
    private final ModelMapper modelMapper;

    @Autowired
    FindFilmsByActorController(FilmsService filmsService, ConfigProperties configProperties, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/v1/findFilmsByActor")
    public ResponseEntity<List<FilmDTO>> fetchFilmsByActor(@Valid
                                                           @ApiParam("String of actor to search; case-insensitive")
                                                           @Pattern(regexp = NAME_REGEX)
                                                           @RequestParam String actor) {
        return this.filmsService.fetchFilmsByActor(actor)
                .map(films -> ResponseEntity
                        .ok()
                        .location(URI.create(configProperties.getFindFilmsByActorURI()))
                        .body(films
                                .stream()
                                .map(film -> modelMapper.map(film, FilmDTO.class))
                                .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }
}
