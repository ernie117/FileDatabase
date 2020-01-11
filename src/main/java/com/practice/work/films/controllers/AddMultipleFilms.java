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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@ResponseStatus(HttpStatus.CREATED)
@Api(tags = {"Add Multiple Films"})
public class AddMultipleFilms {

    private FilmsService filmsService;
    private ModelMapper modelMapper;
    private ConfigProperties configProperties;

    @Autowired
    AddMultipleFilms(FilmsService filmsService, ModelMapper modelMapper, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
        this.configProperties = configProperties;
    }

    @PostMapping(value = "/v1/addMultipleFilms")
    public ResponseEntity<?> insertManyFilmDocuments(@Valid
                                                     @ApiParam("Array of filmDTO objects, structured as in the example")
                                                     @RequestBody List<FilmDTO> filmDTOS) {
        List<Film> filmsToSave = filmDTOS.stream().map(filmDTO -> modelMapper.map(filmDTO, Film.class)).collect(Collectors.toList());

        return filmsService.insertMultipleFilmDocument(filmsToSave).map(films -> {
            try {
                log.info("Fetch All Films endpoint retrieved {} film object/s from DB.", films.size());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                        .location(new URI(configProperties.getAddMultipleFilmsURI()))
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
