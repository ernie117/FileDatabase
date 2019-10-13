package com.practice.work.films.controllers;

import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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
    public FilmDTO insertFilmDocument(@Valid
                                      @ApiParam("Film json object, structured as in the example")
                                      @RequestBody com.practice.work.films.dtos.FilmDTO filmDto) {
        return modelMapper.map(
                filmsService.insertSingleFilmDocument(
                        modelMapper.map(filmDto, Film.class)
                ), FilmDTO.class);
    }
}
