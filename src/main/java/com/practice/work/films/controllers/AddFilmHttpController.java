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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@Api(tags = {"Add Film via HTTP call"})
@Validated
public class AddFilmHttpController {

    private FilmsService filmsService;
    private ModelMapper modelMapper;

    @Autowired
    AddFilmHttpController(FilmsService filmsService, ModelMapper modelMapper) {
        this.filmsService = filmsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/v1/addFilmHttp")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FilmDTO> insertFilmDocumentHttp(@RequestParam(name = "title")
                                                          @ApiParam(value = "Title", required = true)
                                                          @Pattern(regexp = "[a-zA-Z\\d\\s]+") String title,
                                                          @RequestParam(name = "genre")
                                                          @ApiParam(value = "Genre", required = true)
                                                          @Pattern(regexp = "[a-zA-Z\\s]+") String genre,
                                                          @RequestParam(name = "director")
                                                          @ApiParam(value = "Director", required = true)
                                                          @Pattern(regexp = "[a-zA-Z\\s]+") String director,
                                                          @RequestParam(name = "cinematographer")
                                                          @ApiParam(value = "Cinematographer", required = true)
                                                          @Pattern(regexp = "[a-zA-Z\\s]+") String cinematographer,
                                                          @RequestParam(name = "writer")
                                                          @ApiParam(value = "Writer", required = true)
                                                          @Pattern(regexp = "[a-zA-Z\\s]+") String writer,
                                                          @RequestParam(name = "composer")
                                                          @ApiParam(value = "Composer", required = true)
                                                          @Pattern(regexp = "[a-zA-Z\\s]+") String composer,
                                                          @RequestParam(name = "releaseDate")
                                                          @ApiParam(value = "Release Date", required = true)
                                                                  String releaseDate,
                                                          @RequestParam(name = "actors")
                                                          @ApiParam(value = "Actors", required = true) List<String> actors) {

        FilmDTO filmDTO = FilmDTO.builder()
                .title(title)
                .genre(genre)
                .director(director)
                .cinematographer(cinematographer)
                .writer(writer)
                .composer(composer)
                .releaseDate(LocalDate.parse(releaseDate))
                .dateAdded(LocalDateTime.now())
                .actors(actors)
                .build();

        Optional<Film> returnedFilm = filmsService.insertSingleFilmDocument(modelMapper.map(filmDTO, Film.class));

        return returnedFilm
                .map(film -> ResponseEntity
                        .ok()
                        .body(modelMapper.map(film, FilmDTO.class)))
                .orElse(ResponseEntity.unprocessableEntity().build());
    }
}
