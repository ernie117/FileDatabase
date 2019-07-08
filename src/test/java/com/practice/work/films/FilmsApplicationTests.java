package com.practice.work.films;

import com.practice.work.films.controllers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmsApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private TestController testController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    /**
     * Sanity-checking that context (controllers and mongo repositories) is being loaded
     */
    @Test
    public void contextLoads() {
        assertThat(new Object[]{testController, mockMvc}).doesNotContainNull();
    }

    /**
     * Testing that the TestController returns expected test string
     * @throws Exception dunno
     */
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/v1/testing"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("hello world")));
    }

    /**
     * Asserting that service returns proper JSON value
     * @throws Exception dunno
     */
    @Test
    public void returnCorrectTestEntityValue() throws Exception {
        this.mockMvc.perform(get("/v1/testingEntity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testString").value("This is a test"));

    }
}
