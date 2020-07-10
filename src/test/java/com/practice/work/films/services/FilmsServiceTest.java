package com.practice.work.films.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.practice.work.films.constants.TestConstants.OBJECT_MAPPER;
import static com.practice.work.films.constants.TestConstants.TEST_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FilmsServiceTest {

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

    private static IntStream indexSource() {
        return IntStream.of(0, 1, 2);
    }

    private static Stream<Arguments> titlesAndIds() {
        return Stream.of(
        );
    }

    @ParameterizedTest
    @MethodSource("indexSource")
    void insertSingleFilmDocument(int index) {
        Mockito.when(filmRepository.save(FILMS.get(index))).thenReturn(FILMS.get(index));
        Optional<Film> result = filmsService.insertSingleFilmDocument(FILMS.get(index));

        assertEquals(FILMS.get(index), result.orElse(Film.builder().build()));
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
            assertEquals("123456789", jsonNode.get(0).get("test title one").asText());
            assertEquals("12635463829", jsonNode.get(1).get("test title three").asText());
            assertEquals("987654321", jsonNode.get(2).get("test title two").asText());
        });
    }
}