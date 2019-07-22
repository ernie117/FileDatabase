package com.practice.work.films.controllers;

import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.Film;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = "Fetch Films by Actor")
@Validated
public class FindFilmsByActorController {

    private FilmsService filmsService;

    @Autowired
    FindFilmsByActorController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping("/v1/findFilmsByActor")
    public List<Film> fetchFilmsByActor(@Valid
                                        @ApiParam("String of actor to search; case-insensitive")
                                        @Pattern(regexp = "[a-zA-Z]+")
                                        @RequestParam String actor) {
        return this.filmsService.fetchFilmsByActor(actor);
    }

}
