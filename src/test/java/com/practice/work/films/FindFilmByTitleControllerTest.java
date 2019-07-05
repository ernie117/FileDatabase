package com.practice.work.films;

import com.practice.work.films.controllers.FindFilmByTitleController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FindFilmByTitleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindFilmByTitleController findFilmByTitleController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(findFilmByTitleController).build();
    }

    @Test
    public void testAddFilm() throws Exception {
        this.mockMvc.perform(get("/v1/findFilmByTitle")
                .param("title", "test film"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test film"));
    }
}
