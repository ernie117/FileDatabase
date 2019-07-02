package com.practice.work.films.controllers;

import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReturnAllFilmsController {

    private FilmRepository filmRepository;

    @Autowired
    ReturnAllFilmsController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Film> allFilms() {
        return filmRepository.findAll();
    }
}
