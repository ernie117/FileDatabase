package com.practice.work.films.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
class Violation {

    private String field;
    private String message;

}
