package com.practice.work.films;

import com.practice.work.films.service.FilmsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FilmsApplicationTests {

    @Autowired
    private FilmsService filmsService;

    /**
     * Sanity-checking that context is being loaded
     */
    @Test
    public void contextLoads() {
        assertNotNull(filmsService);
    }
}
