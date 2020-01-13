package com.practice.work.films.controllers;

import com.practice.work.films.dtos.FilmDTO;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
import org.hamcrest.Matchers;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AddFilmHttpControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper mockModelMapper;

    private FilmDTO filmDto;
    private Film film;

    private static final LocalDate release = LocalDate.of(2000, 1, 1);
    private static final LocalDateTime dateAdded = LocalDateTime.of(2020, 1, 1, 12, 0, 10);
    private static final List<String> actors = Arrays.asList("test actor1", "test actor2");

    @BeforeEach
    void setUp() {

        filmDto = FilmDTO.builder()
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

    }


    @Test
    void insertFilmDocumentHttp_CorrectValues() throws Exception {
        doReturn(Optional.of(film)).when(filmsService).insertSingleFilmDocument(film);
        doReturn(film).when(mockModelMapper).map(any(), eq(Film.class));
        doReturn(filmDto).when(mockModelMapper).map(any(), eq(FilmDTO.class));

        String response = this.mockMvc.perform(post("/v1/addFilmHttp")
                .param("title", "test title")
                .param("cinematographer", "test cinematographer")
                .param("composer", "test composer")
                .param("writer", "test writer")
                .param("director", "test director")
                .param("genre", "test genre")
                .param("releaseDate", release.toString())
                .param("actors", actors.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.genre").value("test genre"))
                .andExpect(jsonPath("$.composer").value("test composer"))
                .andExpect(jsonPath("$.writer").value("test writer"))
                .andExpect(jsonPath("$.cinematographer").value("test cinematographer"))
                .andExpect(jsonPath("$.releaseDate").value(release.toString()))
                .andExpect(jsonPath("$.dateAdded").value(dateAdded.toString()))
                .andExpect(jsonPath("$.actors", Matchers.isA(List.class)))
                .andExpect(jsonPath("$.actors[0]").value("test actor1"))
                .andExpect(jsonPath("$.actors[1]").value("test actor2"))
                .andExpect(jsonPath("$.actors.length()", is(2)))
                .andExpect(jsonPath("$.director").value("test director"))
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
        doReturn(Optional.empty()).when(filmsService).insertSingleFilmDocument(film);

        this.mockMvc.perform(post("/v1/addFilmHttp")
                .param("title", "test title")
                .param("cinematographer", "test cinematographer")
                .param("composer", "test composer")
                .param("writer", "test writer")
                .param("director", "test director")
                .param("genre", "test genre")
                .param("releaseDate", release.toString())
                .param("actors", actors.toString()))
                .andExpect(status().isUnprocessableEntity());
    }
}
