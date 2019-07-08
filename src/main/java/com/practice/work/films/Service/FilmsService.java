package com.practice.work.films.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practice.work.films.entities.ConfirmedDeletionMessage;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmsService {

    private final FilmRepository filmRepository;

    @Autowired
    FilmsService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film insertSingleFilmDocument(Film film) {
        return this.filmRepository.save(film);
    }

    public List<Film> insertMultipleFilmDocument(List<Film> films) {
        return Collections.unmodifiableList(
                this.filmRepository.saveAll(films)
                        .stream()
                        .sorted(Film.BY_TITLE)
                        .collect(Collectors.toList())
        );
    }

    public JsonNode getFilmIdsByTitle(String title) {
        ObjectMapper mapper = new ObjectMapper();
        List<Film> films = this.filmRepository.findFilmByTitleRegexIgnoreCase(title);

        ArrayNode rootNode = mapper.createArrayNode();
        for (Film film : films) {
            rootNode.add(mapper.createObjectNode().put(film.getTitle(), film.getId()));
        }

        return rootNode;
    }

    public List<Film> findFilmsByTitleRegexIgnoreCase(String title) {
        return Collections.unmodifiableList(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));
    }

    public ConfirmedDeletionMessage deleteFilmById(String id) {
        String title = this.filmRepository.findFilmById(id).getTitle();
        this.filmRepository.deleteFilmById(id);
        return new ConfirmedDeletionMessage(title);
    }

    public List<Film> findFilmsByDirector(String director) {
        return Collections.unmodifiableList(this.filmRepository.findAllByDirectorRegexIgnoreCase(director));
    }

    public List<Film> findFilmsByGenre(String genre) {
        return Collections.unmodifiableList(this.filmRepository.findFilmsByGenreRegexIgnoreCase(genre));
    }

    public List<Film> findFilmsByYear(String year) {
        return Collections.unmodifiableList(this.filmRepository.findFilmsByYearReleased(year));
    }

    public List<Film> fetchAllFilms() {
        return Collections.unmodifiableList(this.filmRepository.findAll());
    }

    private List<Film> capitalizeTitle(List<Film> films) {
        return films.stream()
                .peek(f -> f.setTitle(WordUtils.capitalize(f.getTitle())))
                .collect(Collectors.toList());
    }
}
