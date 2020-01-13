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

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = "Fetch Films by Writer")
@Validated
public class FindFilmsByWriterController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;
    private ModelMapper modelMapper;

    @Autowired
    FindFilmsByWriterController(FilmsService filmsService, ConfigProperties configProperties, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/v1/findFilmsByWriter")
    public ResponseEntity<List<FilmDTO>> fetchFilmsByWriter(@Valid
                                                            @ApiParam("String of writer to search; case-insensitive")
                                                            @Pattern(regexp = "[a-zA-Z\\s]+")
                                                            @RequestParam String writer) {
        return this.filmsService.fetchFilmsByWriter(writer)
                .map(films -> ResponseEntity
                        .ok()
                        .location(URI.create(configProperties.getFindFilmsByWriterURI()))
                        .body(films
                                .stream()
                                .map(film -> modelMapper.map(film, FilmDTO.class))
                                .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

}

