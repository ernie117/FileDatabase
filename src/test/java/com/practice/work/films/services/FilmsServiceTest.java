package com.practice.work.films.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.practice.work.films.entities.ConfirmedDeletionMessage;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import com.practice.work.films.service.FilmsService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.practice.work.films.constants.TestConstants.OBJECT_MAPPER;
import static com.practice.work.films.constants.TestConstants.TEST_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FilmsServiceTest {

    private static final String TITLE_1 = "test title one";
    private static final String TITLE_2 = "test title two";
    private static final String TITLE_3 = "test title three";

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

        result.ifPresent(message -> {
            assertThat(message, instanceOf(ConfirmedDeletionMessage.class));
            assertEquals(title, message.getTitle());
            assertEquals(String.format("'%s': successfully deleted.", title), message.getMessage());
        });
    }

    @Test
    void testReturnAllFilms() {
        Mockito.when(filmRepository.findAll()).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.fetchAllFilms();

        assertEquals(FILMS, result.orElse(Collections.emptyList()));
    }

    @Test
    void testGetFilmIdByTitle() {
        Mockito.when(filmRepository.findFilmByTitleRegexIgnoreCase("test")).thenReturn(FILMS);
        Optional<JsonNode> result = filmsService.getFilmIdsByTitle("test");

        result.ifPresent(jsonNode -> {
            assertEquals("123456789", jsonNode.get(0).get(TITLE_1).asText());
            assertEquals("12635463829", jsonNode.get(1).get(TITLE_3).asText());
            assertEquals("987654321", jsonNode.get(2).get(TITLE_2).asText());
        });
    }

    @Test
    void testFindFilmsByDirector() {
        Mockito.when(filmRepository.findAllByDirectorRegexIgnoreCase("test")).thenReturn(FILMS);
        Optional<List<Film>> result = filmsService.findFilmsByDirector("test");

        result.ifPresent(films -> {
            assertEquals("test director one", films.get(0).getDirector());
            assertEquals("test director three", films.get(1).getDirector());
            assertEquals("test director two", films.get(2).getDirector());
        });
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
}
