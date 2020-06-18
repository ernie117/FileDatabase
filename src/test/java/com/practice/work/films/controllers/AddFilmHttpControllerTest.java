package com.practice.work.films.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import com.practice.work.films.validation.Violation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.practice.work.films.constants.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.data.mongodb.database=embeddedDB", "logging.level.root=OFF"})
@AutoConfigureMockMvc
class AddFilmHttpControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper mockModelMapper;

    @Test
    void insertFilmDocumentHttp_CorrectValues() throws Exception {
        doReturn(Optional.of(TEST_FILM)).when(filmsService).insertSingleFilmDocument(TEST_FILM);
        doReturn(TEST_FILM).when(mockModelMapper).map(any(), eq(Film.class));
        doReturn(TEST_FILM_DTO).when(mockModelMapper).map(any(), eq(FilmDTO.class));

        this.mockMvc.perform(post("/v1/addFilmHttp")
                .param("title", TEST_TITLE)
                .param("cinematographer", TEST_CINEMATOGRAPHER)
                .param("composer", TEST_COMPOSER)
                .param("writer", TEST_WRITER)
                .param("director", TEST_DIRECTOR)
                .param("genre", TEST_GENRE)
                .param("releaseDate", TEST_RELEASE_DATE.toString())
                .param("actors", TEST_DATE_ADDED.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(TEST_TITLE))
                .andExpect(jsonPath("$.genre").value(TEST_GENRE))
                .andExpect(jsonPath("$.composer").value(TEST_COMPOSER))
                .andExpect(jsonPath("$.writer").value(TEST_WRITER))
                .andExpect(jsonPath("$.cinematographer").value(TEST_CINEMATOGRAPHER))
                .andExpect(jsonPath("$.releaseDate").value(TEST_RELEASE_DATE.toString()))
                .andExpect(jsonPath("$.dateAdded").value(TEST_DATE_ADDED.toString()))
                .andExpect(jsonPath("$.actors", Matchers.isA(List.class)))
                .andExpect(jsonPath("$.actors[0]").value(TEST_ACTORS.get(0)))
                .andExpect(jsonPath("$.actors[1]").value(TEST_ACTORS.get(1)))
                .andExpect(jsonPath("$.actors.length()", is(2)))
                .andExpect(jsonPath("$.director").value(TEST_DIRECTOR))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    /**
     * If, for whatever reason, the DB encounters an error and returns no film object
     * then the add film endpoint will return an unprocessable entity response
     *
     * @throws Exception MockMvc's perform throws generic Exception
     */
    @Test
    void testInsertFilmDocumentHttp_EmptyFilmReturnsUnprocessableEntity() throws Exception {
        doReturn(Optional.empty()).when(filmsService).insertSingleFilmDocument(TEST_FILM);

        this.mockMvc.perform(post("/v1/addFilmHttp")
                .param("title", TEST_TITLE)
                .param("cinematographer", TEST_CINEMATOGRAPHER)
                .param("composer", TEST_COMPOSER)
                .param("writer", TEST_WRITER)
                .param("director", TEST_DIRECTOR)
                .param("genre", TEST_GENRE)
                .param("releaseDate", TEST_RELEASE_DATE.toString())
                .param("actors", TEST_ACTORS.toString()))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testAddFilmHttp_MissingField_ReturnsViolationsWithDetails() throws Exception {
        // Cinematographer missing
        String response = this.mockMvc.perform(post("/v1/addFilmHttp/")
                .param("title", TEST_TITLE)
                .param("composer", TEST_COMPOSER)
                .param("writer", TEST_WRITER)
                .param("director", TEST_DIRECTOR)
                .param("genre", TEST_GENRE)
                .param("releaseDate", TEST_RELEASE_DATE.toString())
                .param("actors", TEST_ACTORS.toString()))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        try {
            Set<Violation> violations = OBJECT_MAPPER.readValue(response, new TypeReference<HashSet<Violation>>() {
            });

            violations.forEach(v -> {
                assertThat(v.getMessage()).isEqualTo("Required String parameter 'cinematographer' is not present");
                assertThat(v.getField()).isEqualTo("cinematographer");
            });

        } catch (JsonProcessingException ex) {
            fail("Exception when processing JSON.", ex.getCause());
        }
    }

    @Test
    void testAddFilmHttp_InvalidField_ReturnsViolationsWithDetails() throws Exception {
        String response = this.mockMvc.perform(post("/v1/addFilmHttp/")
                .param("title", TEST_TITLE)
                .param("composer", "111") // Can't just be numbers
                .param("cinematographer", TEST_CINEMATOGRAPHER)
                .param("writer", TEST_WRITER)
                .param("director", TEST_DIRECTOR)
                .param("genre", TEST_GENRE)
                .param("releaseDate", TEST_RELEASE_DATE.toString())
                .param("actors", TEST_ACTORS.toString()))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        try {
            Set<Violation> violations = OBJECT_MAPPER.readValue(response, new TypeReference<HashSet<Violation>>() {
            });

            violations.forEach(v -> {
                assertThat(v.getMessage()).isEqualTo("must match \"[a-zA-Z\\s]+\"");
                assertThat(v.getField()).isEqualTo("composer");
            });

        } catch (JsonProcessingException ex) {
            fail("Exception when processing JSON.", ex.getCause());
        }
    }

    @Test
    void testAddFilmHttp_BadDate_ReturnsViolationsWithDetails() throws Exception {
        String response = this.mockMvc.perform(post("/v1/addFilmHttp/")
                .param("title", TEST_TITLE)
                .param("cinematographer", TEST_CINEMATOGRAPHER)
                .param("composer", TEST_COMPOSER)
                .param("writer", TEST_WRITER)
                .param("director", TEST_DIRECTOR)
                .param("genre", TEST_GENRE)
                .param("releaseDate", "20-1-1")
                .param("actors", TEST_DATE_ADDED.toString()))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        try {
            Set<Violation> violations = OBJECT_MAPPER.readValue(response, new TypeReference<HashSet<Violation>>() {
            });

            violations.forEach(v -> {
                assertThat(v.getField()).isEqualTo("DateTimeParseException");
                assertThat(v.getMessage()).isEqualTo("Text '20-1-1' could not be parsed at index 0");
            });

        } catch (JsonProcessingException ex) {
            fail("Exception when processing JSON.", ex.getCause());
        }
    }

}
