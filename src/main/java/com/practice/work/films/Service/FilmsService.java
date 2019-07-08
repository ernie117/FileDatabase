package com.practice.work.films.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmsService {

    private final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private final Set<ConstraintViolation<Film>> CONSTRAINT_VIOLATIONS = new HashSet<>();

    private FilmRepository filmRepository;

    @Autowired
    FilmsService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film insertSingleFilmDocument(Film film) {
        runValidationRules(Collections.singletonList(film));
        return this.filmRepository.save(film);
    }

    public List<Film> insertMultipleFilmDocument(List<Film> films) {
        runValidationRules(films);
        return this.filmRepository.saveAll(films);
    }

    public JsonNode getFilmIdsByTitle(String title) {
        List<Film> films = Collections.unmodifiableList(this.filmRepository.findFilmByTitleRegexIgnoreCase(title));
        Map<String, String> titleAndIds = films.stream().collect(Collectors.toMap(Film::getTitle, Film::getId));
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode node = mapper.createArrayNode();

        for (Map.Entry<String, String> entry : titleAndIds.entrySet()) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put(entry.getKey(), entry.getValue());
            node.add(objectNode);
        }

        return node.size() == 1 ? node.get(0) : node;
    }

    public JsonNode findFilmsByTitleRegexIgnoreCase(String title) {
        final List<Film> films = this.filmRepository.findFilmByTitleRegexIgnoreCase(title)
                .stream()
                .peek(f -> f.setTitle(WordUtils.capitalize(f.getTitle())))
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(films, JsonNode.class);

        return node.size() == 1 ? node.get(0) : node;
    }

    public String deleteFilmById(String id) {
        String title = this.filmRepository.findFilmById(id).getTitle();
        this.filmRepository.deleteFilmById(id);
        return String.format("'%s' document deleted from database :)", title);
    }

    public List<Film> findFilmsByDirector(String director) {
        return Collections.unmodifiableList(this.filmRepository.findAllByDirectorRegexIgnoreCase(director));
    }

    private void runValidationRules(List<Film> films) {
        for (Film film : films) {
            CONSTRAINT_VIOLATIONS.addAll(VALIDATOR.validate(film));
        }

        if (!CONSTRAINT_VIOLATIONS.isEmpty()) {
            Set<String> stringViolations = CONSTRAINT_VIOLATIONS
                    .stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.toSet());

            throw new RuntimeException("Film is not valid: " + String.join("\n", stringViolations));
        }

    }
}
