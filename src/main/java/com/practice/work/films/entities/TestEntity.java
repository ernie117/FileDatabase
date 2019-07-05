package com.practice.work.films.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TestEntity {

    @ApiModelProperty("test string")
    private String testString = "This is a test";
}
