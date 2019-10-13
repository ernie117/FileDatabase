package com.practice.work.films.controllers;

import com.practice.work.films.configuration.ConfigProperties;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@ResponseStatus(HttpStatus.OK)
@Api(tags = "Fetch Films by Composer")
@Validated
public class FindFilmsByComposerController {

    private FilmsService filmsService;
    private ConfigProperties configProperties;

    @Autowired
    FindFilmsByComposerController(FilmsService filmsService, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.configProperties = configProperties;
    }

    @GetMapping("/v1/findFilmsByComposer")
    public ResponseEntity<?> fetchFilmsByActor(@Valid
                                               @ApiParam("String of composer to search; case-insensitive")
                                               @Pattern(regexp = "[a-zA-Z]+")
                                               @RequestParam String composer) {
        return this.filmsService.fetchFilmsByComposer(composer)
                .map(films -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                                .location(new URI(configProperties.getFindFilmsByComposerURI()))
                                .body(films);
                    } catch (URISyntaxException use) {
                        return ResponseEntity.badRequest().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }
}
