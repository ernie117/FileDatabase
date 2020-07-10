package com.practice.work.films.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Violation {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private String field;
    private String message;

}
