package com.practice.work.films.repositories;

import com.practice.work.films.entities.Film;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends MongoRepository<Film, String> {

    // save/saveAll methods exist as default methods

    Film findFilmById(String id);

    @Query("{'title': {$regex: ?0}}")
    List<Film> findFilmByTitleRegexIgnoreCase(String title);

    @Query("{'director': {$regex: ?0}}")
    List<Film> findAllByDirectorRegexIgnoreCase(String director);

    @Query("{'genre': {$regex: ?0}}")
    List<Film> findFilmsByGenreRegexIgnoreCase(String genre);

    List<Film> findAllByReleaseDate(LocalDate date);
    List<Film> findAll();

    @Query("{'actors': {$regex: ?0}}")
    List<Film> findFilmsByActorsRegex(String actor);

    @Query("{'composer': {$regex: ?0}}")
    List<Film> findFilmsByComposerRegex(String actor);

    @Query("{'cinematographer': {$regex: ?0}}")
    List<Film> findFilmsByCinematographerRegex(String actor);

    @Query("{'writer': {$regex: ?0}}")
    List<Film> findFilmsByWriterRegex(String actor);

    void deleteFilmById(String id);
}
