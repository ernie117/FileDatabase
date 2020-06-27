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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.practice.work.films.constants.FilmsConstants.NAME_REGEX;


@Slf4j
@RestController
@Api(tags = {"Add Film via HTTP call"})
@Validated
public class AddFilmHttpController {

    private final FilmsService filmsService;
    private final ModelMapper modelMapper;
    private final ConfigProperties configProperties;

    @Autowired
    AddFilmHttpController(FilmsService filmsService, ModelMapper modelMapper, ConfigProperties configProperties) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
        this.configProperties = configProperties;
    }

    @PostMapping(value = "/v1/addFilmHttp")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FilmDTO> insertFilmDocumentHttp(@RequestParam(name = "title")
                                                          @ApiParam(value = "Title", required = true) String title,
                                                          @RequestParam(name = "genre")
                                                          @ApiParam(value = "Genre", required = true) List<String> genre,
                                                          @RequestParam(name = "director")
                                                          @ApiParam(value = "Director", required = true)
                                                          @Pattern(regexp = NAME_REGEX) String director,
                                                          @RequestParam(name = "cinematographer")
                                                          @ApiParam(value = "Cinematographer", required = true)
                                                          @Pattern(regexp = NAME_REGEX) String cinematographer,
                                                          @RequestParam(name = "writers")
                                                          @ApiParam(value = "Writer", required = true) List<String> writers,
                                                          @RequestParam(name = "composer")
                                                          @ApiParam(value = "Composer", required = true)
                                                          @Pattern(regexp = NAME_REGEX) String composer,
                                                          @RequestParam(name = "releaseDate")
                                                          @ApiParam(value = "Release Date", required = true)
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String releaseDate,
                                                          @RequestParam(name = "actors")
                                                          @ApiParam(value = "Actors", required = true) List<String> actors) {

        FilmDTO filmDTO = FilmDTO.builder()
                .title(title)
                .genre(genre)
                .director(director)
                .cinematographer(cinematographer)
                .writers(writers)
                .composer(composer)
                .releaseDate(LocalDate.parse(releaseDate))
                .dateAdded(LocalDateTime.now())
                .actors(actors)
                .build();

        log.info("Adding new film with title: {}", filmDTO.getTitle());

        Optional<Film> returnedFilm = filmsService.insertSingleFilmDocument(modelMapper.map(filmDTO, Film.class));

        return returnedFilm
                .map(film -> ResponseEntity
                        .created(URI.create(configProperties.getAddFilmHttpURI()))
                        .body(modelMapper.map(film, FilmDTO.class)))
                .orElse(ResponseEntity.unprocessableEntity().build());
    }
}
