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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmsService {

    private final FilmRepository filmRepository;

    @Autowired
    FilmsService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    /**
     * Query that inserts a single Film document in MongoDB
     * @param film: object to be inserted into the DB
     * @return returns a copy of the inserted Film object
     */
    public Film insertSingleFilmDocument(Film film) {
        return this.filmRepository.save(film);
    }

    /**
     * Query that inserts multiple film records into MongoDB
     * @param films: list of films to add to MongoDB
     * @return returns a copy of param List<Film>
     */
    public List<Film> insertMultipleFilmDocument(List<Film> films) {
        return sortAndWrap(this.filmRepository.saveAll(films));
    }

    /**
     * Query that returns Json objects of film titles IDs matching a given title
     * @param title: title of the film search; case-insensitive
     * @return JsonNode of film title and ID
     */
    public JsonNode getFilmIdsByTitle(String title) {
        ObjectMapper mapper = new ObjectMapper();
        List<Film> films = sortAndWrap(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));

        ArrayNode rootNode = mapper.createArrayNode();
        for (Film film : films) {
            rootNode.add(mapper.createObjectNode().put(film.getTitle(), film.getId()));
        }

        return rootNode;
    }

    /**
     * Query that deletes film document by ID from MongoDB
     * @param id: ID matching film document to delete
     * @return ConfirmedDeletionMessage object representing deleted document
     */
    public ConfirmedDeletionMessage deleteFilmById(String id) {
        String title = this.filmRepository.findFilmById(id).getTitle();
        this.filmRepository.deleteFilmById(id);

        return new ConfirmedDeletionMessage(title);
    }

    /**
     * Queries MongoDB for all films that match a given string, by regex
     * @param title: title of the film query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByTitleRegexIgnoreCase(String title) {
        return sortAndWrap(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));
    }

    /**
     * Queries MongoDB for all films with a given director
     * @param director: name of the director to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByDirector(String director) {
        return sortAndWrap(this.filmRepository.findAllByDirectorRegexIgnoreCase(director));
    }

    /**
     * Queries MongoDB for all films with a given genre
     * @param genre: genre to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByGenre(String genre) {
        return sortAndWrap(this.filmRepository.findFilmsByGenreRegexIgnoreCase(genre));
    }

    /**
     * Queries MongoDB for all films with a given release year
     * @param date: value of the release year to query
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByReleaseDate(LocalDate date) {
        return sortAndWrap(this.filmRepository.findAllByReleaseDate(date));
    }

    /**
     * Queries MongoDB for all films
     * @return sorted, immutable List<Film>
     */
    public List<Film> fetchAllFilms() {
        return sortAndWrap(this.filmRepository.findAll());
    }

    /**
     * Queries MongoDB for films by actor
     * @param actor: string of actor to search
     * @return sorted, immutable List<Film>
     */
    public List<Film> fetchFilmsByActor(String actor) {
        return sortAndWrap(this.filmRepository.findFilmsByActorsRegex(actor));
    }

    // TODO method to find by composer
    // TODO method to find by cinematographer
    // TODO method to find by writer

    /**
     * Sorts given List<Film> by title, wraps it in an unmodifiable List<>
     * @param films, list of films
     * @return Sorted, immutable List<Film>
     */
    private List<Film> sortAndWrap(List<Film> films) {
        return Collections.unmodifiableList(films
                .stream()
                .sorted(Film.BY_TITLE)
                .collect(Collectors.toList())
        );
    }
}
