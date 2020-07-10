package com.practice.work.films.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.practice.work.films.constants.FilmsConstants.NAME_REGEX;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {

    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @NotBlank
    @ApiModelProperty(
            name = "title",
            example = "string",
            position = 1
    )
    private String title;

    @NotEmpty
    @ApiModelProperty(
            name = "genre",
            example = "['string', 'string']",
            position = 2
    )
    private List<String> genre;

    @NotBlank
    @Pattern(regexp = NAME_REGEX)
    @ApiModelProperty(
            name = "director",
            example = "string",
            position = 3
    )
    private String director;

    @NotBlank
    @Pattern(regexp = NAME_REGEX)
    @ApiModelProperty(
            name = "cinematographer",
            example = "string",
            position = 4
    )
    private String cinematographer;

    @NotEmpty
    @ApiModelProperty(
            name = "writer",
            example = "['string', 'string']",
            position = 5
    )
    private List<@Pattern(regexp = NAME_REGEX) String> writers;

    @NotBlank
    @Pattern(regexp = NAME_REGEX)
    @ApiModelProperty(
            name = "composer",
            example = "string",
            position = 6
    )
    private String composer;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    @ApiModelProperty(
            name = "releaseDate",
            example = "1999-01-31",
            position = 7)
    private LocalDate releaseDate;

    @NotEmpty
    @ApiModelProperty(
            name = "actors",
            example = "['string', 'string']",
            position = 8)
    @Size(min = 1, max = 10)
    private List<@Pattern(regexp = NAME_REGEX) String> actors;

    @ApiModelProperty(hidden = true)
    @CreatedDate
    private LocalDateTime dateAdded;

}

