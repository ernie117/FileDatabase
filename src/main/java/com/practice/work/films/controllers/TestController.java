package com.practice.work.films.controllers;

import com.practice.work.films.entities.TestEntity;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.OK)
@NoArgsConstructor
public class TestController {

    @GetMapping(value = "/v1/testing", produces = "application/json")
    public TestEntity getTestResponse() {
        return new TestEntity();
    }
}
