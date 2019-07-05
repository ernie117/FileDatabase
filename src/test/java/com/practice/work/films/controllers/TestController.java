package com.practice.work.films.controllers;

import com.practice.work.films.entities.TestEntity;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ResponseStatus(HttpStatus.OK)
@NoArgsConstructor
@ApiIgnore
public class TestController {

    @GetMapping(value = "/v1/testing", produces = "application/json")
    public String getTestResponse() {
        return "hello world";
    }

    @GetMapping(value = "/v1/testingEntity", produces = "application/json")
    public TestEntity getTestEntityResponse() {
        return new TestEntity();
    }
}
