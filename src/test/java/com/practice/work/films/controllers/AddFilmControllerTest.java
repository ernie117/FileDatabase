package com.practice.work.films.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.BeforeEach;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AddFilmControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper mockModelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private FilmDTO filmDto;
    private Film film;
    private String filmDtoAsString;

    private static final String randomUUID = UUID.randomUUID().toString();
    private static final LocalDate release = LocalDate.of(2000, 1, 1);
    private static final LocalDateTime dateAdded = LocalDateTime.now();

    @BeforeEach
    void setUp() throws IOException {

        List<String> actors = Arrays.asList("test actor1", "test actor2");

        filmDto = FilmDTO.builder()
                .id(randomUUID)
                .title("test title")
                .cinematographer("test cinematographer")
                .composer("test composer")
                .writer("test writer")
                .director("test director")
                .genre("test genre")
                .releaseDate(release)
                .dateAdded(dateAdded)
                .actors(actors)
                .build();

        film = Film.builder()
                .id(randomUUID)
                .title("test title")
                .cinematographer("test cinematographer")
                .composer("test composer")
                .writer("test writer")
                .director("test director")
                .genre("test genre")
                .releaseDate(release)
                .dateAdded(dateAdded)
                .actors(actors)
                .build();

        filmDtoAsString = objectMapper.writeValueAsString(filmDto);
    }

    @Test
    void testInsertFilmDocument_CorrectValues() throws Exception {
        doReturn(Optional.of(film)).when(filmsService).insertSingleFilmDocument(film);
        doReturn(film).when(mockModelMapper).map(filmDto, Film.class);
        doReturn(filmDto).when(mockModelMapper).map(film, FilmDTO.class);

        String response = this.mockMvc.perform(post("/v1/addFilm/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(filmDtoAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath(".id").value(randomUUID))
                .andExpect(jsonPath(".title").value("test title"))
                .andExpect(jsonPath(".genre").value("test genre"))
                .andExpect(jsonPath(".composer").value("test composer"))
                .andExpect(jsonPath(".writer").value("test writer"))
                .andExpect(jsonPath(".cinematographer").value("test cinematographer"))
                .andExpect(jsonPath(".releaseDate").value(release.toString()))
                .andExpect(jsonPath(".dateAdded").value(dateAdded.toString()))
                .andExpect(jsonPath(".actors[0]").value("test actor1"))
                .andExpect(jsonPath(".actors[1]").value("test actor2"))
                .andExpect(jsonPath(".director").value("test director"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, String> object = JsonPath.read(response, "$");
        assertThat(object.keySet().size()).isEqualTo(10);
    }

    /**
     * If, for whatever reason, the DB encounters an error and returns no film object
     * then the add film endpoint will return an unprocessable entity response
     *
     * @throws Exception MockMvc's perform throws generic Exception
     */
    @Test
    void testInsertFilmDocument_EmptyFilmReturnsUnprocessableEntity() throws Exception {
        doReturn(Optional.empty()).when(filmsService).insertSingleFilmDocument(film);

        this.mockMvc.perform(post("/v1/addFilm/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(filmDtoAsString))
                .andExpect(status().isUnprocessableEntity());
    }
}
