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

    // TODO method to find by actor
    // TODO method to find by composer
    // TODO method to find by cinematographer
    // TODO method to find by writer

    void deleteFilmById(String id);
}
