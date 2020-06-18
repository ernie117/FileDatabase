package com.practice.work.films.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.practice.work.films.entities.Film;
import com.practice.work.films.service.FilmsService;
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

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.data.mongodb.database=embeddedDB", "logging.level.root=OFF"})
@AutoConfigureMockMvc
class ReturnAllFilmsControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    // Necessary for deserializing LocalDate
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final File TEST_JSON = Paths.get("src", "test", "resources", "test.json").toFile();

    private static List<Film> films;
    private static List<String> writers;

    @BeforeAll
    static void setupObjectMapper() throws Exception {
        films = MAPPER.readValue(TEST_JSON, new TypeReference<>() {
        });
        writers = JsonPath.read(TEST_JSON, "$[*].writer");
    }

    @Test
    @DisplayName("GET /v1/all")
    void testReturnAllFilms() throws Exception {
        doReturn(Optional.of(films)).when(filmsService).fetchAllFilms();

        String response = this.mockMvc.perform(get("/v1/all"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.LOCATION, "/v1/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[0].title").value("test title one"))
                .andExpect(jsonPath("[1].title").value("test title two"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<String> writerResult = JsonPath.read(response, "$[*].writer");
        assertThat(writerResult).isEqualTo(writers);

    }

    @Test
    @DisplayName("GET /v1/all -- Not Found")
    void testEmptyRepository() throws Exception {
        doReturn(Optional.empty()).when(filmsService).fetchAllFilms();

        this.mockMvc.perform(get("/v1/all"))
                .andExpect(status().isNotFound());
    }
}