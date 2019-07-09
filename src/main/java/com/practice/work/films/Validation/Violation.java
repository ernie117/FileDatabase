package com.practice.work.films.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
class Violation {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private String field;
    private String message;

}
