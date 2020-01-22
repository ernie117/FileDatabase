package com.practice.work.films.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.work.films.entities.Film;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class FilmRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FilmRepository filmRepository;

    // Necessary for deserializing LocalDate
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final File TEST_JSON = Paths.get("src", "test", "resources", "test.json").toFile();

    @BeforeEach
    void setupObjectMapper() throws IOException {
        List<Film> films = objectMapper.readValue(TEST_JSON, new TypeReference<List<Film>>() {
        });
        films.forEach(mongoTemplate::save);
    }

    @AfterEach
    void afterEach() {
        mongoTemplate.dropCollection("films");
    }

    @Test
    void testFindAll() {
        List<Film> films = filmRepository.findAll();
        assertEquals(2, films.size(), "Should be two");
        // Assert films are returned sorted
        List<Film> sortedFilms = films.stream().sorted(Film.BY_TITLE).collect(Collectors.toList());
        assertThat(sortedFilms, is(films));
    }

    @Test
    void testFindAllByTitleRegex() {
        Optional<List<Film>> films = Optional.ofNullable(filmRepository.findFilmByTitleRegexIgnoreCase("test title"));
        assertTrue(films.isPresent(), "We should return the films from the JSON file");
        System.out.println(films.get());
        films.ifPresent(filmsList -> {
            Film film1 = filmsList.get(0);
            assertEquals("test title1", film1.getTitle(), "Should be test title1");
            assertNotEquals("false genre", film1.getGenre(), "Should be test genre1");
            Film film2 = filmsList.get(1);
            assertEquals("test title2", film2.getTitle(), "Should be test title2");
            assertNotEquals("false cinematographer", film1.getCinematographer(), "Should be test cinematographer1");
        });
    }

    @Test
    void testFindAllByDirectorRegex() {
        Optional<List<Film>> films = Optional.ofNullable(filmRepository.findAllByDirectorRegexIgnoreCase("test director"));
        assertTrue(films.isPresent(), "We should return the films matching the director in the Json file");
        films.ifPresent((filmsList -> {
            assertEquals("test director1", filmsList.get(0).getDirector(), "Should be test director1");
            assertEquals("test director2", filmsList.get(1).getDirector(), "Should be test director2");
        }));
    }

    @Test
    void findAllFilmsByGenreRegex() {
        Optional<List<Film>> films = Optional.ofNullable(filmRepository.findFilmsByGenreRegexIgnoreCase("test genre"));
        assertTrue(films.isPresent(), "We should return the films matching the genre in the JSON file");
        films.ifPresent((filmsList -> {
            assertEquals("test genre1", filmsList.get(0).getGenre(), "Should be test genre1");
            assertEquals("test genre2", filmsList.get(1).getGenre(), "Should be test genre2");
        }));
    }

    @Test
    void findAllFilmsByReleaseDate() {
        Optional<List<Film>> films = Optional.ofNullable(filmRepository.findAllByReleaseDate(LocalDate.parse("2000-01-31")));
        assertTrue(films.isPresent(), "We should return the film matching the release date in the JSON file");
        films.ifPresent((filmsList -> {
            assertEquals("2000-01-31", filmsList.get(0).getReleaseDate().toString(), "Should be 2000-01-31");
            assertNotEquals("2019-01-01", filmsList.get(0).getReleaseDate().toString(), "Should be 2000-01-31");
        }));
    }

    @Test
    void findAllFilmsByActors() {
        Optional<List<Film>> films = Optional.ofNullable(filmRepository.findFilmsByActorsRegex("test actor"));
        assertTrue(films.isPresent(), "We should return the films matching one of the actors in the Json file");
        films.ifPresent((filmsList -> {
            assertEquals("test actor one", filmsList.get(0).getActors().get(0), "Should be test actor one");
            assertEquals("test actor two", filmsList.get(0).getActors().get(1), "Should be test actor two");
        }));
    }

    @Test
    void deleteFilmById() {
        Film filmToDelete = filmRepository.findAll().get(0);
        String id = filmToDelete.getId();
        filmRepository.deleteFilmById(id);
        Optional<Film> shouldBeDeleted = Optional.ofNullable(filmRepository.findFilmById(id));
        assertFalse(shouldBeDeleted.isPresent());
    }
}
