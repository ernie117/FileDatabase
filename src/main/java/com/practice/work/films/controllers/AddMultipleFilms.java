package com.practice.work.films.controllers;

import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@ResponseStatus(HttpStatus.CREATED)
@Api(tags = {"Add Multiple Films"})
public class AddMultipleFilms {

    private FilmsService filmsService;
    private ModelMapper modelMapper;

    @Autowired
    AddMultipleFilms(FilmsService filmsService, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/v1/addMultipleFilms")
    public List<FilmDTO> insertManyFilmDocuments(@Valid
                                                 @ApiParam("Array of filmDTO objects, structured as in the example")
                                                 @RequestBody List<FilmDTO> filmDTOS) {
        return Collections.singletonList(
                modelMapper.map(
                        this.filmsService.insertMultipleFilmDocument(
                                filmDTOS.stream()
                                        .map(filmDTO -> modelMapper.map(filmDTO, Film.class))
                                        .collect(Collectors.toList())),
                        FilmDTO.class)
        );
    }
}
