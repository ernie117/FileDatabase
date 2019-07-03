package com.practice.work.films.repositories;

import com.practice.work.films.entities.Film;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FilmRepository extends MongoRepository<Film, String> {

    // save/saveAll methods exist as default methods

    @Query("{'title': {$regex: ?0}}")
    List<Film> findFilmByTitleRegexIgnoreCase(String title);
    List<Film> findAllByDirectorIgnoreCase(String director);
    List<Film> findFilmsByGenreIgnoreCase(String genre);
    List<Film> findFilmsByYearReleased(int year);
    List<Film> findAll();

}
