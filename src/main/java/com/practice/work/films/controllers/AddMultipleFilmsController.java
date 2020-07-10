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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@Api(tags = {"Add Multiple Films"})
@Validated
public class AddMultipleFilmsController {

    private final FilmsService filmsService;
    private final ModelMapper modelMapper;
    private final ConfigProperties configProperties;

    @Autowired
    AddMultipleFilmsController(FilmsService filmsService, ModelMapper modelMapper, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
        this.configProperties = configProperties;
    }

    @PostMapping(value = "/v1/addMultipleFilms")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<FilmDTO>> insertManyFilmDocuments(@Valid
                                                                 @ApiParam("Array of filmDTO objects, structured as in the example")
                                                                 @RequestBody List<FilmDTO> filmDTOS) {
        log.info("Add Multiple Films endpoint called with titles: {}",
                Arrays.deepToString(filmDTOS.stream().map(FilmDTO::getTitle).toArray()));
        List<Film> filmsToSave = filmDTOS.stream()
                .map(filmDTO -> modelMapper.map(filmDTO, Film.class))
                .collect(Collectors.toList());

        return filmsService.insertMultipleFilmDocument(filmsToSave).map(films -> {
            log.info("Add Multiple Films endpoint reflects {} film objects from DB.", films.size());
            return ResponseEntity
                    .created(URI.create(configProperties.getAddMultipleFilmsURI()))
                    .location(URI.create(configProperties.getAddMultipleFilmsURI()))
                    .body(films
                            .stream()
                            .map(film -> modelMapper.map(film, FilmDTO.class))
                            .collect(Collectors.toList()));
        }).orElse(ResponseEntity.unprocessableEntity().build());
    }
}
