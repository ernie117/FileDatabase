package com.practice.work.films.controllers;

import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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
import java.util.Optional;


@RestController
@Api(tags = {"Add Film"})
@Validated
public class AddFilmController {

    private FilmsService filmsService;
    private ModelMapper modelMapper;

    @Autowired
    AddFilmController(FilmsService filmsService, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/v1/addFilm")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> insertFilmDocument(@Valid
                                                @ApiParam("Film json object, structured as in the example")
                                                @RequestBody FilmDTO filmDto) {
        Optional<Film> film = filmsService.insertSingleFilmDocument(modelMapper.map(filmDto, Film.class));

        return film
                .map(result -> ResponseEntity
                        .ok()
                        .body(modelMapper.map(result, FilmDTO.class)))
                .orElse(ResponseEntity.unprocessableEntity().build());
    }
}

