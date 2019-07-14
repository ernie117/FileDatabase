package com.practice.work.films;

import com.practice.work.films.Service.FilmsService;
import com.practice.work.films.controllers.FindFilmByTitleController;
import com.practice.work.films.entities.Film;
import com.practice.work.films.repositories.FilmRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FindFilmByTitleController.class)
public class FindFilmByTitleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private FilmsService filmsService;

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

    @Test
    public void testFindByFilmByRegex() throws Exception {
        given(this.filmsService.findFilmsByTitleRegexIgnoreCase("test title"))
                .willReturn(testFilmList);
        this.mockMvc.perform(get("/v1/findFilmByTitle")
                .param("title", "test title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test title"));
    }
}
