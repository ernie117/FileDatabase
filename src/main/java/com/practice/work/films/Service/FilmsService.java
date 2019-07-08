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

    /**
     * Query that inserts a single Film document in MongoDB
     * @param Film object to be inserted into the DB
     * @return returns a copy of the inserted Film object
     */
    public Film insertSingleFilmDocument(Film film) {
        return this.filmRepository.save(film);
    }

    /**
     * Query that inserts multiple film records into MongoDB
     * @param List<Film> list of films to add to MongoDB
     * @return returns a copy of param List<Film>
     */
    public List<Film> insertMultipleFilmDocument(List<Film> films) {
        return Collections.unmodifiableList(
                this.filmRepository.saveAll(films)
                        .stream()
                        .sorted(Film.BY_TITLE)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Query that returns Json objects of film titles IDs matching a given title
     * @param String value representing file title to search; case-insensitive
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
     * @param String value of ID matching film document to delete
     * @return ConfirmedDeletionMessage object representing deleted document
     */
    public ConfirmedDeletionMessage deleteFilmById(String id) {
        String title = this.filmRepository.findFilmById(id).getTitle();
        this.filmRepository.deleteFilmById(id);

        return new ConfirmedDeletionMessage(title);
    }

    /**
     * Queries MongoDB for all films that match a given string, by regex
     * @param String value of the title to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByTitleRegexIgnoreCase(String title) {
        return sortAndWrap(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));
    }

    /**
     * Queries MongoDB for all films with a given director
     * @param String value of the director to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByDirector(String director) {
        return sortAndWrap(this.filmRepository.findAllByDirectorRegexIgnoreCase(director));
    }

    /**
     * Queries MongoDB for all films with a given genre
     * @param String value of the genre to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByGenre(String genre) {
        return sortAndWrap(this.filmRepository.findFilmsByGenreRegexIgnoreCase(genre));
    }

    /**
     * Queries MongoDB for all films with a given release year
     * @param String value of the release year to query
     * @return sorted, immutable List<Film>
     */
    public List<Film> findFilmsByYear(String year) {
        return sortAndWrap(this.filmRepository.findFilmsByYearReleased(year));
    }

    /**
     * Queries MongoDB for all films
     * @return sorted, immutable List<Film>
     */
    public List<Film> fetchAllFilms() {
        return sortAndWrap(this.filmRepository.findAll());
    }

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

    // TODO method to find by actor
    // TODO method to find by composer
    // TODO method to find by cinematographer
    // TODO method to find by writer

}
