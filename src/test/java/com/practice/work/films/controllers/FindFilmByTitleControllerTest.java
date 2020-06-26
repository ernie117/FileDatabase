package com.practice.work.films.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.practice.work.films.constants.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.data.mongodb.database=embeddedDB", "logging.level.root=OFF"})
@AutoConfigureMockMvc
class FindFilmByTitleControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    private static List<Film> TEST_FILMS;

    @BeforeAll
    static void setup() throws IOException {
        TEST_FILMS = OBJECT_MAPPER.readValue(TEST_JSON, new TypeReference<>() {
        });
    }

    @Test
    @DisplayName("GET /v1/findFilmByTitle")
    void testFindByFilmByRegex() throws Exception {
        doReturn(Optional.of(TEST_FILMS)).when(filmsService).findFilmsByTitleRegexIgnoreCase(TEST_TITLE);

        this.mockMvc.perform(get("/v1/findFilmByTitle")
                .param("title", TEST_TITLE))

                // validate response and data type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // validate headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/v1/findFilmByTitle"))

                // validate fields in response for first film
                .andExpect(jsonPath("$[0]['title']").value("test title one"))
                .andExpect(jsonPath("$[0]['director']").value("test director one"))
                .andExpect(jsonPath("$[0]['cinematographer']").value("test cinematographer one"))
                .andExpect(jsonPath("$[0]['composer']").value("test composer one"))
                .andExpect(jsonPath("$[0]['writers']", Matchers.isA(List.class)))
                .andExpect(jsonPath("$[0]['writers'][0]").value("test writer one"))
                .andExpect(jsonPath("$[0]['writers'][1]").value("test writer two"))
                .andExpect(jsonPath("$[0]['genre']", Matchers.isA(List.class)))
                .andExpect(jsonPath("$[0]['genre'][0]").value("test genre one"))
                .andExpect(jsonPath("$[0]['genre'][1]").value("test genre two"))
                .andExpect(jsonPath("$[0]['genre'][2]").value("test genre three"))
                .andExpect(jsonPath("$[0]['releaseDate']").value(LocalDate.of(2000, 1, 31).toString()))
                .andExpect(jsonPath("$[0]['releaseDate']", isA(String.class)))
                .andExpect(jsonPath("$[0]['actors']", isA(List.class)))
                .andExpect(jsonPath("$[0]['actors'][0]").value("test actor one"))
                .andExpect(jsonPath("$[0]['actors'][1]").value("test actor two"))
                .andExpect(jsonPath("$[0]['actors'].length()", is(2)))

                // validate fields in response for second film
                .andExpect(jsonPath("$[1]['title']").value("test title two"))
                .andExpect(jsonPath("$[1]['director']").value("test director two"))
                .andExpect(jsonPath("$[1]['cinematographer']").value("test cinematographer two"))
                .andExpect(jsonPath("$[1]['composer']").value("test composer two"))
                .andExpect(jsonPath("$[1]['writers']", Matchers.isA(List.class)))
                .andExpect(jsonPath("$[1]['writers'][0]").value("test writer three"))
                .andExpect(jsonPath("$[1]['writers'][1]").value("test writer four"))
                .andExpect(jsonPath("$[1]['genre']", Matchers.isA(List.class)))
                .andExpect(jsonPath("$[1]['genre'][0]").value("test genre four"))
                .andExpect(jsonPath("$[1]['genre'][1]").value("test genre five"))
                .andExpect(jsonPath("$[1]['genre'][2]").value("test genre six"))
                .andExpect(jsonPath("$[1]['releaseDate']").value(LocalDate.of(2000, 1, 1).toString()))
                .andExpect(jsonPath("$[1]['releaseDate']", isA(String.class)))
                .andExpect(jsonPath("$[1]['actors']", isA(List.class)))
                .andExpect(jsonPath("$[1]['actors'][0]").value("test actor three"))
                .andExpect(jsonPath("$[1]['actors'][1]").value("test actor four"))
                .andExpect(jsonPath("$[1]['actors'].length()", is(2)));
    }

    @Test
    @DisplayName("GET /v1/findFilmByTitle -- Not found")
    void testFindNonExistentFilmByRegex() throws Exception {
        doReturn(Optional.empty()).when(filmsService).findFilmsByTitleRegexIgnoreCase("empty");

        this.mockMvc.perform(get("/v1/findFilmByTitle")
                .param("title", "empty"))
                .andExpect(status().isNotFound());

    }
}
