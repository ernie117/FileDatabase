package com.practice.work.films.controllers;

import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.ConfirmedDeletionMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = {"Delete Film by ID"})
public class DeleteFilmController {

    private FilmsService filmsService;

    @Autowired
    DeleteFilmController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @DeleteMapping(path = "/v1/deleteFilmByTitle")
    public ConfirmedDeletionMessage deleteFilmById(String id) {
        return this.filmsService.deleteFilmById(id);
    }

}
