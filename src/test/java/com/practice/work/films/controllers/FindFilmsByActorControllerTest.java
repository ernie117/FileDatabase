package com.practice.work.films.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.practice.work.films.constants.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FindFilmsByActorControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    private static List<Film> TEST_FILM_1 = new ArrayList<>();

    @BeforeAll
    static void setup() throws IOException {
        List<Film> TEST_FILMS = OBJECT_MAPPER.readValue(TEST_JSON, new TypeReference<>() {
        });
        TEST_FILM_1.add(TEST_FILMS.get(0));
    }
    @Test
    void fetchFilmsByActor_CorrectValues() throws Exception {
        doReturn(Optional.of(TEST_FILM_1)).when(filmsService).fetchFilmsByActor(TEST_ACTORS.get(0));

        this.mockMvc.perform(get("/v1/findFilmsByActor")
                .param("actor", TEST_ACTORS.get(0)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, "/v1/findFilmsByActor"))
                .andExpect(jsonPath("[0]['title']").value("test title1"))
                .andExpect(jsonPath("[0]['director']").value("test director1"))
                .andExpect(jsonPath("[0]['cinematographer']").value("test cinematographer1"))
                .andExpect(jsonPath("[0]['composer']").value("test composer1"))
                .andExpect(jsonPath("[0]['writer']").value("test writer1"))
                .andExpect(jsonPath("[0]['genre']").value("test genre1"))
                .andExpect(jsonPath("[0]['releaseDate']").value(LocalDate.of(2000, 1, 31).toString()))
                .andExpect(jsonPath("[0]['releaseDate']", isA(String.class)))
                .andExpect(jsonPath("[0]['actors']", isA(List.class)))
                .andExpect(jsonPath("[0]['actors'][0]").value("test actor one"))
                .andExpect(jsonPath("[0]['actors'][1]").value("test actor two"))
                .andExpect(jsonPath("[0]['actors'].length()", is(2)));
    }
}