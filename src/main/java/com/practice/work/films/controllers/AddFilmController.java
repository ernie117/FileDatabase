package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@Slf4j
@RestController
@Api(tags = {"Add Film"})
@Validated
public class AddFilmController {

    private final FilmsService filmsService;
    private final ModelMapper modelMapper;
    private final ConfigProperties configProperties;

    @Autowired
    AddFilmController(FilmsService filmsService, ModelMapper modelMapper, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
        this.configProperties = configProperties;
    }

    @PostMapping(value = "/v1/addFilm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FilmDTO> insertFilmDocument(@Valid
                                                      @ApiParam("Film json object, structured as in the example")
                                                      @RequestBody FilmDTO filmDto) {
        log.info("Adding new film with title: {}", filmDto.getTitle());
        Optional<Film> film = filmsService.insertSingleFilmDocument(modelMapper.map(filmDto, Film.class));

        return film
                .map(result -> ResponseEntity
                        .created(URI.create(configProperties.getAddFilmURI()))
                        .body(modelMapper.map(result, FilmDTO.class)))
                .orElse(ResponseEntity.unprocessableEntity().build());
    }
}

