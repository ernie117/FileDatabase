package com.practice.work.films.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.practice.work.films.constants.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AddMultipleFilmsTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper mockModelMapper;

    public static String FILM_DTO_AS_STRING;
    private static String FILM_DTOS_AS_STRING;
    private static List<Film> TEST_FILMS;
    private static List<FilmDTO> TEST_FILM_DTOS;

    @BeforeAll
    static void setup() throws IOException {
        TEST_FILMS = OBJECT_MAPPER.readValue(TEST_JSON, new TypeReference<List<Film>>() {
        });
        TEST_FILM_DTOS = OBJECT_MAPPER.readValue(TEST_JSON, new TypeReference<List<FilmDTO>>() {
        });
        FILM_DTO_AS_STRING = OBJECT_MAPPER.writeValueAsString(TEST_FILM_DTO);
        FILM_DTOS_AS_STRING = OBJECT_MAPPER.writeValueAsString(TEST_FILM_DTOS);
    }

    @Test
    void insertManyFilmDocuments_CorrectValues() throws Exception {
        doReturn(Optional.of(TEST_FILMS)).when(filmsService).insertMultipleFilmDocument(TEST_FILMS);
        doReturn(TEST_FILMS.get(0), TEST_FILMS.get(1)).when(mockModelMapper).map(any(), eq(Film.class));
        doReturn(TEST_FILM_DTOS.get(0), TEST_FILM_DTOS.get(1)).when(mockModelMapper).map(any(), eq(FilmDTO.class));

        this.mockMvc.perform(post("/v1/addMultipleFilms")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(FILM_DTOS_AS_STRING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[0].*", hasSize(10)))
                .andExpect(jsonPath("[1].*", hasSize(10)));
    }

    @Test
    void insertManyFilmDocuments_Error() throws Exception {
        doReturn(Optional.empty()).when(filmsService).insertMultipleFilmDocument(TEST_FILMS);

        this.mockMvc.perform(post("/v1/addMultipleFilms")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(FILM_DTOS_AS_STRING))
                .andExpect(status().isUnprocessableEntity());
    }
}
