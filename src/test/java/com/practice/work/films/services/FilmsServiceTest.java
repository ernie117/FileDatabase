package com.practice.work.films.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.practice.work.films.entities.ConfirmedDeletionMessage;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.practice.work.films.constants.TestConstants.OBJECT_MAPPER;
import static com.practice.work.films.constants.TestConstants.TEST_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FilmsServiceTest {

    private static final String TITLE_1 = "test title one";
    private static final String TITLE_2 = "test title two";
    private static final String TITLE_3 = "test title three";
    private static final String TEST_CONST = "test";

    private static List<Film> FILMS = new ArrayList<>();

    @MockBean
    private FilmRepository filmRepository;

    @Autowired
    private FilmsService filmsService;

    @BeforeAll
    static void setup() throws IOException {
        FILMS = OBJECT_MAPPER.readValue(TEST_JSON, new TypeReference<>() {
        });
        FILMS.sort(Film.BY_TITLE);
    }

    @ParameterizedTest
    @MethodSource("indexSource")
    void insertSingleFilmDocument(int index) {
        Mockito.when(filmRepository.save(FILMS.get(index))).thenReturn(FILMS.get(index));
        Optional<Film> result = filmsService.insertSingleFilmDocument(FILMS.get(index));

        assertEquals(FILMS.get(index), result.orElse(Film.builder().build()));
    }

    @Test
    void testInsertMultipleFilms() {
        Mockito.when(filmRepository.saveAll(FILMS)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.insertMultipleFilmDocument(FILMS);

        assertEquals(FILMS, result.orElse(Collections.emptyList()));
    }

    @ParameterizedTest
    @MethodSource("titlesAndIndices")
    void testDeleteFilmById(String title, int index) {
        Mockito.when(filmRepository.findFilmById(FILMS.get(index).getId())).thenReturn(FILMS.get(index));
        Mockito.doNothing().when(filmRepository).deleteFilmById(FILMS.get(index).getTitle());

        Optional<ConfirmedDeletionMessage> result = filmsService.deleteFilmById(FILMS.get(index).getId());

        result.ifPresentOrElse(message -> assertAll(() -> {
            assertThat(message, instanceOf(ConfirmedDeletionMessage.class));
            assertEquals(title, message.getTitle());
            assertEquals(String.format("'%s': successfully deleted.", title), message.getMessage());
        }), Assertions::fail);
    }

    @Test
    void testReturnAllFilms() {
        Mockito.when(filmRepository.findAll()).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.fetchAllFilms();

        assertEquals(FILMS, result.orElse(Collections.emptyList()));
    }

    @Test
    void testFindFilmsByTitle() {
        Mockito.when(filmRepository.findFilmByTitleRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.findFilmsByTitleRegexIgnoreCase(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertEquals(TITLE_1, films.get(0).getTitle());
            assertEquals(TITLE_3, films.get(1).getTitle());
            assertEquals(TITLE_2, films.get(2).getTitle());
        }), Assertions::fail);
    }

    @Test
    void testGetFilmIdByTitle() {
        Mockito.when(filmRepository.findFilmByTitleRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<ArrayNode> result = filmsService.getFilmIdsByTitle(TEST_CONST);
        List<String> expectedFields = Arrays.asList(TITLE_1, TITLE_3, TITLE_2);

        result.ifPresentOrElse(jsonNode -> assertAll(() -> {
            assertEquals(expectedFields, getJsonNodeFields(jsonNode));
            assertEquals("aab25a9b-36cb-4b09-84b9-73a6eacb668e", jsonNode.get(0).get(TITLE_1).asText());
            assertEquals("98a69335-6c06-42a3-942d-78ef4a93e2c5", jsonNode.get(1).get(TITLE_3).asText());
            assertEquals("21941fda-2c3a-4d73-948e-695ef5bc6607", jsonNode.get(2).get(TITLE_2).asText());
        }), Assertions::fail);
    }

    @Test
    void testFindFilmsByDirector() {
        Mockito.when(filmRepository.findAllByDirectorRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.findFilmsByDirector(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertFalse(films.isEmpty());
            assertEquals("test director one", films.get(0).getDirector());
            assertEquals("test director three", films.get(1).getDirector());
            assertEquals("test director two", films.get(2).getDirector());
        }), Assertions::fail);
    }

    @Test
    void testFindFilmsByGenre() {
        Mockito.when(filmRepository.findFilmsByGenreRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.findFilmsByGenre(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertFalse(films.isEmpty(), "List should be populated.");
            assertEquals(FILMS.get(0).getGenre(), films.get(0).getGenre());
            assertEquals(FILMS.get(1).getGenre(), films.get(1).getGenre());
            assertEquals(FILMS.get(2).getGenre(), films.get(2).getGenre());
        }), Assertions::fail);
    }

    @ParameterizedTest
    @MethodSource("datesSource")
    void testFindFilmsByReleaseDate(String date, int index) {
        Mockito.when(filmRepository.findAllByReleaseDate(LocalDate.parse(date))).thenReturn(Collections.singletonList(FILMS.get(index)));
        Optional<List<Film>> result = filmsService.findFilmsByReleaseDate(LocalDate.parse(date));

        result.ifPresentOrElse(films -> assertEquals(FILMS.get(index), films.get(0)), Assertions::fail);
    }

    @ParameterizedTest
    @MethodSource("yearsAndIndices")
    void testFindFilmsByReleaseYear(String year, int index) {
        LocalDate from = LocalDate.of(Integer.parseInt(year), 1, 1);
        LocalDate to = from.plusYears(1);
        Mockito.when(filmRepository.findAllFilmsByYear(from, to)).thenReturn(Collections.singletonList(FILMS.get(index)));
        Optional<List<Film>> result = filmsService.findFilmsByReleaseYear(year);

        result.ifPresentOrElse(film -> assertEquals(FILMS.get(index), film.get(0)), Assertions::fail);
    }

    @Test
    void testFindFilmsByActor() {
        Mockito.when(filmRepository.findFilmsByActorsRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.fetchFilmsByActor(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertEquals(FILMS, films);
            assertEquals(films.get(0).getActors(), FILMS.get(0).getActors());
            assertEquals(films.get(1).getActors(), FILMS.get(1).getActors());
            assertEquals(films.get(2).getActors(), FILMS.get(2).getActors());
        }), Assertions::fail);
    }

    @Test
    void testFindFilmsByComposer() {
        Mockito.when(filmRepository.findFilmsByComposerRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.fetchFilmsByComposer(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertEquals(FILMS, films);
            assertEquals(films.get(0).getComposer(), FILMS.get(0).getComposer());
            assertEquals(films.get(1).getComposer(), FILMS.get(1).getComposer());
            assertEquals(films.get(2).getComposer(), FILMS.get(2).getComposer());
        }), Assertions::fail);
    }

    @Test
    void testFindFilmsByCinematographer() {
        Mockito.when(filmRepository.findFilmsByCinematographerRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.fetchFilmsByCinematographer(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertEquals(FILMS, films);
            assertEquals(films.get(0).getCinematographer(), FILMS.get(0).getCinematographer());
            assertEquals(films.get(1).getCinematographer(), FILMS.get(1).getCinematographer());
            assertEquals(films.get(2).getCinematographer(), FILMS.get(2).getCinematographer());
        }), Assertions::fail);
    }

    @Test
    void testFindFilmsByWriter() {
        Mockito.when(filmRepository.findFilmsByWriterRegexIgnoreCase(TEST_CONST)).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.fetchFilmsByWriter(TEST_CONST);

        result.ifPresentOrElse(films -> assertAll(() -> {
            assertEquals(FILMS, films);
            assertEquals(films.get(0).getWriters(), FILMS.get(0).getWriters());
            assertEquals(films.get(1).getWriters(), FILMS.get(1).getWriters());
            assertEquals(films.get(2).getWriters(), FILMS.get(2).getWriters());
        }), Assertions::fail);
    }

    private List<String> getJsonNodeFields(ArrayNode node) {
        List<String> fields = new ArrayList<>();

        for (JsonNode jsonNode : node) {
            for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext(); ) {
                fields.add(it.next().getKey());
            }
        }

        return fields;
    }

    private static Stream<Arguments> datesSource() {
        return Stream.of(
                Arguments.of("2000-01-31", 0),
                Arguments.of("2010-01-01", 1),
                Arguments.of("1994-01-01", 2)
        );
    }

    private static IntStream indexSource() {
        return IntStream.of(0, 1, 2);
    }

    private static Stream<Arguments> titlesAndIndices() {
        return Stream.of(
                Arguments.of(TITLE_1, 0),
                Arguments.of(TITLE_3, 1),
                Arguments.of(TITLE_2, 2)
        );
    }

    private static Stream<Arguments> yearsAndIndices() {
        return Stream.of(
                Arguments.of("2000", 0),
                Arguments.of("2010", 1),
                Arguments.of("1994", 2)
        );
    }
}
