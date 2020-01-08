package com.practice.work.films.controllers;

import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@Api(tags = {"Add Film via HTTP call"})
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
    public FilmDTO insertFilmDocumentHttp(@RequestParam(name = "Film's Title")
                                          @ApiParam(value = "Title", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String title,
                                          @RequestParam(name = "Film's Genre")
                                          @ApiParam(value = "Genre", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String genre,
                                          @RequestParam(name = "Film's Director")
                                          @ApiParam(value = "Director", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String director,
                                          @RequestParam(name = "Film's Cinematographer")
                                          @ApiParam(value = "Cinematographer", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String cinematographer,
                                          @RequestParam(name = "Film's Writer")
                                          @ApiParam(value = "Writer", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String writer,
                                          @RequestParam(name = "Film's Composer")
                                          @ApiParam(value = "Composer", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String composer,
                                          @RequestParam(name = "Film's Release Date")
                                          @ApiParam(value = "Release Date", required = true)
                                          @Pattern(regexp = "[a-zA-Z\\s]+") String releaseDate,
                                          @RequestParam(name = "List of the Film's Actors")
                                          @ApiParam(value = "Actors", required = true) List<String> actors) {

        FilmDTO filmDTO = FilmDTO.builder()
                .title(title)
                .genre(genre)
                .director(director)
                .cinematographer(cinematographer)
                .writer(writer)
                .composer(composer)
                .releaseDate(LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern(DateTimeFormat.ISO.DATE.toString())))
                .actors(actors)
                .dateAdded(LocalDateTime.now())
                .build();

        return modelMapper.map(filmsService.insertSingleFilmDocument(modelMapper.map(filmDTO, Film.class)), FilmDTO.class);
    }
}
