package com.practice.work.films.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final File TEST_JSON = Paths.get("src", "test", "resources", "test.json").toFile();

    private static List<Film> films;
    private static List<FilmDTO> filmDtos;
    private static String filmDtosAsString;

    @BeforeAll
    static void setupObjectMapper() throws Exception {
        films = objectMapper.readValue(TEST_JSON, new TypeReference<List<Film>>() {
        });
        filmDtos = objectMapper.readValue(TEST_JSON, new TypeReference<List<FilmDTO>>() {
        });
        filmDtosAsString = objectMapper.writeValueAsString(filmDtos);
    }

    @Test
    void insertManyFilmDocuments_CorrectValues() throws Exception {
        doReturn(Optional.of(films)).when(filmsService).insertMultipleFilmDocument(films);
        doReturn(films.get(0), films.get(1)).when(mockModelMapper).map(any(), eq(Film.class));
        doReturn(filmDtos.get(0), filmDtos.get(1)).when(mockModelMapper).map(any(), eq(FilmDTO.class));

        this.mockMvc.perform(post("/v1/addMultipleFilms")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(filmDtosAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[0].*", hasSize(10)))
                .andExpect(jsonPath("[1].*", hasSize(10)));
    }

    @Test
    void insertManyFilmDocuments_Error() throws Exception {
        doReturn(Optional.empty()).when(filmsService).insertMultipleFilmDocument(films);

        this.mockMvc.perform(post("/v1/addMultipleFilms")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(filmDtosAsString))
                .andExpect(status().isUnprocessableEntity());
    }
}
