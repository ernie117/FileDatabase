package com.practice.work.films.controllers;

import com.practice.work.films.entities.ConfirmedDeletionMessage;
import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.practice.work.films.constants.TestConstants.TEST_ID;
import static com.practice.work.films.constants.TestConstants.TEST_TITLE;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DeleteFilmControllerTest {

    @MockBean
    private FilmsService filmsService;

    @Autowired
    private MockMvc mockMvc;

    private static final String CONFIRM_MSG = String.format("'%s': successfully deleted.", TEST_TITLE);

    private final ConfirmedDeletionMessage confirmation = new ConfirmedDeletionMessage(TEST_TITLE);

    @Test
    void deleteFilmById() throws Exception {
        doReturn(Optional.of(confirmation)).when(filmsService).deleteFilmById(TEST_ID);

        this.mockMvc.perform(delete("/v1/deleteFilmByTitle")
                .param("id", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(TEST_TITLE))
                .andExpect(jsonPath("$.message").value(CONFIRM_MSG));
    }

    @Test
    void deleteFilmById_Error() throws Exception {
        doReturn(Optional.empty()).when(filmsService).deleteFilmById(TEST_ID);

        this.mockMvc.perform(delete("/v1/deleteFilmByTitle")
                .param("id", TEST_ID))
                .andExpect(status().isUnprocessableEntity());
    }
}
