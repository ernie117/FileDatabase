package com.practice.work.films;

import com.practice.work.films.service.FilmsService;
import com.practice.work.films.entities.Film;
import org.apache.commons.lang3.StringUtils;
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

import static org.mockito.BDDMockito.given;
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

    private List<Film> testFilmList = Collections.singletonList(
            Film.builder()
                    .title("test title")
                    .director("test director")
                    .cinematographer("test cinematographer")
                    .composer("test composer")
                    .writer("test writer")
                    .genre("test genre")
                    .releaseDate(LocalDate.parse("2000-01-01"))
                    .actors(Arrays.asList("test actor1", "test actor2"))
                    .build()
    );

    private final String mockFilmDateAdded = String.valueOf(LocalDate.now().toEpochDay());

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
                .andExpect(header().string(HttpHeaders.ETAG, StringUtils.wrap(mockFilmDateAdded, "\"")))
                .andExpect(header().string(HttpHeaders.LOCATION, "/v1/findFilmByTitle"))

                // validate fields in response
                .andExpect(jsonPath("$[0].title").value("test title"))
                .andExpect(jsonPath("$[0].director").value("test director"))
                .andExpect(jsonPath("$[0].cinematographer").value("test cinematographer"))
                .andExpect(jsonPath("$[0].composer").value("test composer"))
                .andExpect(jsonPath("$[0].writer").value("test writer"))
                .andExpect(jsonPath("$[0].genre").value("test genre"))
                .andExpect(jsonPath("$[0].releaseDate").value(LocalDate.of(2000, 1, 1).toString()))
                .andExpect(jsonPath("$[0].actors[0]").value("test actor1"))
                .andExpect(jsonPath("$[0].actors[1]").value("test actor2"));
    }
}
