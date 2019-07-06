package com.practice.work.films;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FilmsService {

    private FilmRepository filmRepository;

    @Autowired
    FilmsService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findFilmsByTitleRegexIgnoreCase(String title) {
        return Collections.unmodifiableList(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));
    }

}
