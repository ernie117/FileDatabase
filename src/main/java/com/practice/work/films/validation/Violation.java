package com.practice.work.films.validation;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Violation {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private String field;
    private String message;

}
