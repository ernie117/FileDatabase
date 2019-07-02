package com.practice.work.films.repositories;

import com.practice.work.films.entities.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FilmRepository extends MongoRepository<Film, String> {

    // save/saveAll methods exist as default methods

    Film findFilmByTitleIgnoreCase(String title);
    List<Film> findAllByDirectorIgnoreCase(String director);
    List<Film> findFilmsByGenreIgnoreCase(String genre);
    List<Film> findFilmsByYearReleased(int year);
    List<Film> findAll();
}
