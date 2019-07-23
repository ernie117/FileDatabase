package com.practice.work.films;

import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FindFilmByTitleControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    private List<Film> testFilmList = Collections.unmodifiableList(
            Arrays.asList(
                Film.builder()
                        .title("test title1")
                        .director("test director1")
                        .cinematographer("test cinematographer1")
                        .composer("test composer1")
                        .writer("test writer1")
                        .genre("test genre1")
                        .releaseDate(LocalDate.parse("2000-01-01"))
                        .actors(Arrays.asList("test actor1", "test actor2"))
                        .build(),
                Film.builder()
                        .title("test title2")
                        .director("test director2")
                        .cinematographer("test cinematographer2")
                        .composer("test composer2")
                        .writer("test writer2")
                        .genre("test genre2")
                        .releaseDate(LocalDate.parse("2002-01-01"))
                        .actors(Arrays.asList("test actor3", "test actor4"))
                        .build()
            )
    );


    @Test
    @DisplayName("GET /v1/findFilmByTitle")
    void testFindByFilmByRegex() throws Exception {
        given(this.filmsService.findFilmsByTitleRegexIgnoreCase("test title"))
                .willReturn(Optional.ofNullable(testFilmList));

        this.mockMvc.perform(get("/v1/findFilmByTitle")
                .param("title", "test title"))

                // validate response and data type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // validate headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/v1/findFilmByTitle"))

                // validate fields in response for first film
                .andExpect(jsonPath("$[0].title").value("test title1"))
                .andExpect(jsonPath("$[0].director").value("test director1"))
                .andExpect(jsonPath("$[0].cinematographer").value("test cinematographer1"))
                .andExpect(jsonPath("$[0].composer").value("test composer1"))
                .andExpect(jsonPath("$[0].writer").value("test writer1"))
                .andExpect(jsonPath("$[0].genre").value("test genre1"))
                .andExpect(jsonPath("$[0].releaseDate").value(LocalDate.of(2000, 1, 1).toString()))
                .andExpect(jsonPath("$[0].releaseDate", isA(String.class)))
                .andExpect(jsonPath("$[0].actors", isA(List.class)))
                .andExpect(jsonPath("$[0].actors[0]").value("test actor1"))
                .andExpect(jsonPath("$[0].actors[1]").value("test actor2"))
                .andExpect(jsonPath("$[0].actors.length()", is(2)))

                // validate fields in response for second film
                .andExpect(jsonPath("$[1].title").value("test title2"))
                .andExpect(jsonPath("$[1].director").value("test director2"))
                .andExpect(jsonPath("$[1].cinematographer").value("test cinematographer2"))
                .andExpect(jsonPath("$[1].composer").value("test composer2"))
                .andExpect(jsonPath("$[1].writer").value("test writer2"))
                .andExpect(jsonPath("$[1].genre").value("test genre2"))
                .andExpect(jsonPath("$[1].releaseDate").value(LocalDate.of(2002, 1, 1).toString()))
                .andExpect(jsonPath("$[1].releaseDate", isA(String.class)))
                .andExpect(jsonPath("$[1].actors", isA(List.class)))
                .andExpect(jsonPath("$[1].actors[0]").value("test actor3"))
                .andExpect(jsonPath("$[1].actors[1]").value("test actor4"))
                .andExpect(jsonPath("$[1].actors.length()", is(2)));
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
