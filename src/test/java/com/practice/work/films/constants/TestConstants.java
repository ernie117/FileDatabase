package com.practice.work.films.constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestConstants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static final String TEST_ID = UUID.randomUUID().toString();
    public static final String TEST_TITLE = "TeSt TItlE";
    public static final String TEST_CINEMATOGRAPHER = "TESt CinEMAtOGraPhER";
    public static final String TEST_COMPOSER = "TESt COmPoSeR";
    public static final List<String> TEST_WRITER = Arrays.asList("tEST WRiTer one", "TEst writER twO");
    public static final String TEST_DIRECTOR = "Test diRECtor";
    public static final List<String> TEST_GENRE = Arrays.asList("teSt GenRe oNe", "test Genre tWO", "TESt GenRe THRee");
    public static final String RANDOM_UUID = UUID.randomUUID().toString();
    public static final LocalDate TEST_RELEASE_DATE = LocalDate.of(2000, 1, 1);
    public static final String TEST_RELEASE_YEAR = "2000";
    public static final LocalDateTime TEST_DATE_ADDED = LocalDateTime.of(2020, 1, 1, 12, 0, 15);
    public static final List<String> TEST_ACTORS = Arrays.asList("tESt ACtor One", "tesT ACtoR TWo");

    public static final File TEST_JSON = Paths.get("src", "test", "resources", "test.json").toFile();

    public static final File INVALID_GENRE_TEST_JSON = Paths.get("src", "test", "resources", "invalid_json.json").toFile();

    public static final FilmDTO TEST_FILM_DTO = FilmDTO.builder()
            .id(RANDOM_UUID)
            .title(TEST_TITLE)
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writers(TEST_WRITER)
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final Film TEST_FILM = Film.builder()
            .id(RANDOM_UUID)
            .title(TEST_TITLE)
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writers(TEST_WRITER)
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final FilmDTO INVALID_TEST_FILM_DTO_BLANK_TITLE = FilmDTO.builder()
            .id(RANDOM_UUID)
            .title("")
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writers(TEST_WRITER)
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final FilmDTO INVALID_TEST_FILM_DTO_INVALID_WRITER = FilmDTO.builder()
            .id(RANDOM_UUID)
            .title(TEST_TITLE)
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writers(Collections.singletonList("111"))
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    // missing a comma
    public static final String BAD_JSON = """
                                          [
                                              {
                                                  "field": "value"
                                                  "field2": "value"
                                              }
                                          ]
                                          """;
}
