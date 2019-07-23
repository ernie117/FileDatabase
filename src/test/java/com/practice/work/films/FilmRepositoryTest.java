package com.practice.work.films;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class FilmRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FilmRepository filmRepository;

    private ObjectMapper MAPPER = new ObjectMapper();

    private static final File TEST_JSON = Paths.get("src", "test", "resources", "test.json").toFile();

    // Necessary for deserializing LocalDate
    @PostConstruct
    void setupObjectMapper() {
        MAPPER.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void beforeEach() throws Exception {
        List<Film> films = MAPPER.readValue(TEST_JSON, new TypeReference<List<Film>>(){});
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
    }

    @Test
    void testFindAllByTitleRegex() {
        Optional<List<Film>> films = Optional.ofNullable(filmRepository.findFilmByTitleRegexIgnoreCase("db test"));
        assertTrue(films.isPresent(), "We should return the films from the JSON file");
        films.ifPresent(filmsList -> {
            Film film1 = filmsList.get(0);
            assertEquals("db test title1", film1.getTitle(), "Should be db test title1");
            assertNotEquals("false genre", film1.getGenre(), "Should be db genre1");
            Film film2 = filmsList.get(1);
            assertEquals("db test title2", film2.getTitle(), "Should be db test title2");
            assertNotEquals("false cinematographer", film1.getCinematographer(), "Should be db cinematographer1");
        });
    }

    @Test
    @Ignore
    void testFindAllByDirectorRegex() {
       assert false;
    }

    @Test
    @Ignore
    void findAllFilmsByGenreRegex() {
        assert false;
    }

    @Test
    @Ignore
    void findAllFilmsByReleaseDate() {
        assert false;
    }

    @Test
    @Ignore
    void findAllFilmsByActors() {
        assert false;
    }

    @Test
    @Ignore
    void deleteFilmById() {
        assert false;
    }
}
