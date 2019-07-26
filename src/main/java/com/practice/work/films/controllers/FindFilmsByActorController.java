package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.Film;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = "Fetch Films by Actor")
@Validated
public class FindFilmsByActorController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;

    @Autowired
    FindFilmsByActorController(FilmsService filmsService, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
    }

    @GetMapping("/v1/findFilmsByActor")
    public ResponseEntity<?> fetchFilmsByActor(@Valid
                                        @ApiParam("String of actor to search; case-insensitive")
                                        @Pattern(regexp = "[a-zA-Z]+")
                                        @RequestParam String actor) {
        return this.filmsService.fetchFilmsByActor(actor)
                .map(films -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                .location(new URI(configProperties.getFindFilmsByActorURI()))
                                .body(films);
                    } catch (URISyntaxException use) {
                        return ResponseEntity.badRequest().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

}
