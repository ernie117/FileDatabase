package com.practice.work.films;

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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

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

    @Autowired
    private FindFilmByTitleController findFilmByTitleController;

    private Film testFilm = Film.builder()
            .title("test film")
            .build();

    @Test
    public void testAddFilm() throws Exception {
        given(this.findFilmByTitleController.fetchFilmByTitle("test film"))
                .willReturn(Collections.singletonList(testFilm));
        MvcResult result = this.mockMvc.perform(get("/v1/findFilmByTitle")
                .param("title", "test film"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test film")).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
