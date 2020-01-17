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
import java.util.List;
import java.util.UUID;

public class TestConstants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static final String TEST_ID = UUID.randomUUID().toString();
    public static final String TEST_TITLE = "test title";
    public static final String TEST_CINEMATOGRAPHER = "test cinematographer";
    public static final String TEST_COMPOSER = "test composer";
    public static final String TEST_WRITER = "test writer";
    public static final String TEST_DIRECTOR = "test director";
    public static final String TEST_GENRE = "test genre";
    public static final String RANDOM_UUID = UUID.randomUUID().toString();
    public static final LocalDate TEST_RELEASE_DATE = LocalDate.of(2000, 1, 1);
    public static final LocalDateTime TEST_DATE_ADDED = LocalDateTime.of(2020, 1, 1, 12, 0, 15);
    public static final List<String> TEST_ACTORS = Arrays.asList("test actor1", "test actor2");

    public static final File TEST_JSON = Paths.get("src", "test", "resources", "test.json").toFile();

    public static final FilmDTO TEST_FILM_DTO = FilmDTO.builder()
            .id(RANDOM_UUID)
            .title(TEST_TITLE)
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writer(TEST_WRITER)
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
            .writer(TEST_WRITER)
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final Film TEST_FILM_2 = Film.builder()
            .title("test title2")
            .director("test director2")
            .cinematographer("test cinematographer2")
            .composer("test composer2")
            .writer("test writer2")
            .genre("test genre2")
            .releaseDate(LocalDate.parse("2002-01-01"))
            .actors(Arrays.asList("test actor3", "test actor4"))
            .build();

    public static final Film INVALID_TEST_FILM = Film.builder()
            .id(RANDOM_UUID)
            .title("") // Should be 'NotBlank'
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writer(TEST_WRITER)
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final FilmDTO INVALID_TEST_FILM_DTO_BLANK_TITLE = FilmDTO.builder()
            .id(RANDOM_UUID)
            .title("") // Should be 'NotBlank'
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writer(TEST_WRITER)
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final FilmDTO INVALID_TEST_FILM_DTO_WRITER = FilmDTO.builder()
            .id(RANDOM_UUID)
            .title(TEST_TITLE) // Should be 'NotBlank'
            .cinematographer(TEST_CINEMATOGRAPHER)
            .composer(TEST_COMPOSER)
            .writer("1111")
            .director(TEST_DIRECTOR)
            .genre(TEST_GENRE)
            .releaseDate(TEST_RELEASE_DATE)
            .dateAdded(TEST_DATE_ADDED)
            .actors(TEST_ACTORS)
            .build();

    public static final String BAD_JSON = "{'field': 'value'";
}
