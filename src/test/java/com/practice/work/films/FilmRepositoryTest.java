package com.practice.work.films;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

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
        Assertions.assertEquals(2, films.size(), "Should be two");
    }
}
