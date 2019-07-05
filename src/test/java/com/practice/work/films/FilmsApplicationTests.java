package com.practice.work.films;

import com.practice.work.films.controllers.*;
import com.practice.work.films.repositories.FilmRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmsApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReturnAllFilmsController returnAllFilmsController;
    @Autowired
    private FindFilmsByDirectorController findFilmsByDirectorController;
    @Autowired
    private FindFilmsByYearController findFilmsByYearController;
    @Autowired
    private FindFilmsByGenreController findFilmsByGenreController;
    @Autowired
    private FindFilmByTitleController findFilmByTitleController;
    @Autowired
    private AddMultipleFilms addMultipleFilms;
    @Autowired
    private AddFilmController addFilmController;

    @Autowired
    private FilmRepository filmRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
    }

    /**
     * Sanity-checking that contexts (controllers and mongo repositories) are being loaded
     */
    @Test
    public void contextLoads() {
        assertThat(new Object[]{returnAllFilmsController,
                                findFilmsByDirectorController,
                                findFilmByTitleController,
                                findFilmsByGenreController,
                                findFilmsByYearController,
                                addFilmController,
                                addMultipleFilms}).doesNotContainNull();

        assertThat(filmRepository).isNotNull();
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/v1/testing")).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(containsString("Hello World")));
    }
}
