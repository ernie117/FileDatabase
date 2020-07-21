package com.practice.work.films.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.practice.work.films.constants.FilmsConstants.NAME_REGEX;
import static java.util.Comparator.comparing;

@Builder
@Document(collection = "films")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Film {

    public static final Comparator<Film> BY_TITLE = comparing(Film::getTitle);

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
            example = "string",
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

    @NotBlank
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

    @Override
    public String toString() {
        return String.format(
                "Film[id=%s, title=%s, genre=%s, director=%s, cinematographer=%s, writer=%s, composer=%s, yearReleased=%s, actors=%s]",
                id, title, genre, director, cinematographer, writers, composer, releaseDate, String.join(", ", actors)
        );
    }
}
