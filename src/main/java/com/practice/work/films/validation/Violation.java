package com.practice.work.films.validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
class Violation {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private String field;
    private String message;

}
