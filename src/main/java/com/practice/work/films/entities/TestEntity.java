package com.practice.work.films.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TestEntity {

    @ApiModelProperty(name = "test string")
    private final String testString = "Hello World";

}
