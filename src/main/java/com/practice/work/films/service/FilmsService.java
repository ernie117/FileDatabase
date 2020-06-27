package com.practice.work.films.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.practice.work.films.entities.ConfirmedDeletionMessage;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
     *
     * @param film: object to be inserted into the DB
     * @return returns a copy of the inserted Film object
     */
    public Optional<Film> insertSingleFilmDocument(Film film) {
        return Optional.of(this.filmRepository.save(film));
    }

    /**
     * Query that inserts multiple film records into MongoDB
     *
     * @param films: list of films to add to MongoDB
     * @return returns a copy of param List<Film>
     */
    public Optional<List<Film>> insertMultipleFilmDocument(List<Film> films) {
        return Optional.of(sortAndWrap(this.filmRepository.saveAll(films)));
    }

    /**
     * Query that returns Json objects of film titles IDs matching a given title
     *
     * @param title: title of the film search; case-insensitive
     * @return JsonNode of film title and ID
     */
    public Optional<JsonNode> getFilmIdsByTitle(String title) {
        ObjectMapper mapper = new ObjectMapper();
        List<Film> films = sortAndWrap(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));

        ArrayNode rootNode = mapper.createArrayNode().addAll(
                films.stream()
                        .map(film -> mapper.createObjectNode().put(film.getTitle(), film.getId()))
                        .collect(Collectors.toList())
        );

        return Optional.ofNullable(rootNode);
    }

    /**
     * Query that deletes film document by ID from MongoDB
     *
     * @param id: ID matching film document to delete
     * @return ConfirmedDeletionMessage object representing deleted document
     */
    public Optional<ConfirmedDeletionMessage> deleteFilmById(String id) {
        String title = this.filmRepository.findFilmById(id).getTitle();
        this.filmRepository.deleteFilmById(id);

        return Optional.of(new ConfirmedDeletionMessage(title));
    }

    /**
     * Queries MongoDB for all films that match a given string, by regex
     *
     * @param title: title of the film query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> findFilmsByTitleRegexIgnoreCase(String title) {
        return Optional.of(sortAndWrap(this.filmRepository.findFilmByTitleRegexIgnoreCase(title)));
    }

    /**
     * Queries MongoDB for all films with a given director
     *
     * @param director: name of the director to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> findFilmsByDirector(String director) {
        return Optional.of(sortAndWrap(this.filmRepository.findAllByDirectorRegexIgnoreCase(director)));
    }

    /**
     * Queries MongoDB for all films with a given genre
     *
     * @param genre: genre to query; case-insensitive
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> findFilmsByGenre(String genre) {
        return Optional.of(sortAndWrap(this.filmRepository.findFilmsByGenreRegexIgnoreCase(genre)));
    }

    /**
     * Queries MongoDB for all films with a given release date
     *
     * @param date: value of the release year to query
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> findFilmsByReleaseDate(LocalDate date) {
        return Optional.of(sortAndWrap(this.filmRepository.findAllByReleaseDate(date)));
    }

    /**
     * Queries MongoDB for all films with a given release year
     *
     * @param date: value of the release year to query
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> findFilmsByReleaseYear(String from) {
        LocalDate fromDate = LocalDate.of(Integer.parseInt(from), 1, 1);
        return Optional.of(sortAndWrap(this.filmRepository.findAllFilmsByYear(fromDate, fromDate.plusYears(1))));
    }

    /**
     * Queries MongoDB for all films
     *
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> fetchAllFilms() {
        return Optional.of(sortAndWrap(this.filmRepository.findAll()));
    }

    /**
     * Queries MongoDB for films by actors
     *
     * @param actor: string of actor to search
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> fetchFilmsByActor(String actor) {
        return Optional.of(sortAndWrap(this.filmRepository.findFilmsByActorsRegexIgnoreCase(actor)));
    }

    /**
     * Queries MongoDB for films by composer
     *
     * @param composer: string of the composer to search
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> fetchFilmsByComposer(String composer) {
        return Optional.of(sortAndWrap(this.filmRepository.findFilmsByComposerRegexIgnoreCase(composer)));
    }

    /**
     * Queries MongoDB for films by cinematographer
     *
     * @param cinematographer: string of the cinematographer to search
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> fetchFilmsByCinematographer(String cinematographer) {
        return Optional.of(sortAndWrap(this.filmRepository.findFilmsByCinematographerRegexIgnoreCase(cinematographer)));
    }

    /**
     * Queries MongoDB for films by writer
     *
     * @param writer: string of the writer to search
     * @return sorted, immutable List<Film>
     */
    public Optional<List<Film>> fetchFilmsByWriter(String writer) {
        return Optional.of(sortAndWrap(this.filmRepository.findFilmsByWriterRegexIgnoreCase(writer)));
    }

    /**
     * Sorts given List<Film> by title, wraps it in an unmodifiable List<>
     *
     * @param films, list of films
     * @return sorted, immutable List<Film>
     */
    private List<Film> sortAndWrap(List<Film> films) {
        return films
                .stream()
                .sorted(Film.BY_TITLE)
                .collect(Collectors.toUnmodifiableList());
    }
}
